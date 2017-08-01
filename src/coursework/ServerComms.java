package coursework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Galvin on 5/14/2015.
 */
//SERVER COMMUNICATIONS
public class ServerComms {
    //to identify which connection
    private int connectionID, port;
    //boolean to keep the server running
    private boolean keepAlive;

    //Arraylist of Clients
    private ArrayList<ClientThread> clientThreads;
    //Arraylist of Users
    private ArrayList<User> users;

    //to interact with the serverGUI
    private ServerGUI serverGUI;

    private SimpleDateFormat sdf;

    AtomicInteger userID;

    public ServerComms(int port) {
        //init server to listen to this port
        this.port = port;
    }

    //Use gui if exist
    public ServerComms(int port, ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
        this.port = port;
        //Arraylist for client list
        clientThreads = new ArrayList<ClientThread>();
        sdf = new SimpleDateFormat("HH:mm:ss");
    }

    //SERVER CODE to start the server
    public void start() {
        //Lets the server run throughout unless specified to stop
        userID = new AtomicInteger(0);
        keepAlive = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (keepAlive) {
                display("Server started, listening on port " + port + ".");

                // accept the connection, this points to client now
                Socket socket = serverSocket.accept();

                if (!keepAlive) {
                    break;
                }

                ClientThread t = new ClientThread(socket);
                clientThreads.add(t);
                t.start();
            }
            try {
                serverSocket.close();
                for (ClientThread clientThread : clientThreads) {
                    try {
                        clientThread.inputStream.close();
                        clientThread.outputStream.close();
                        clientThread.socket.close();
                    } catch (IOException e) {
                        display("Error closing client stream");
                    }
                }
            } catch (IOException e) {
                display("Error occurred while closing server and client :" + e);
            }
        } catch (IOException e) {
            display("Exception on creating server socket : " + e);
        }
    }

    //Stop the server
    protected void stop() {
        keepAlive = false;
        try {
            new Socket("localhost", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This will display with either GUI or CLI
    private void display(String msg) {
        String formatMesssage = sdf.format(new Date()) + " " + msg;
        if (serverGUI == null) {
            System.out.println(formatMesssage);
        } else {
            serverGUI.appendEvent(formatMesssage + "\n");
        }
    }


    //Removes the client after logging out
    synchronized void remove(int id) {
        for (int i = 0; i < clientThreads.size(); ++i) {
            ClientThread ct = clientThreads.get(i);

            //ID found, remove it from the arraylist of client threads
            if (ct.id == id) {
                clientThreads.remove(i);
                return;
            }
        }
    }

    private synchronized void broadcast(String message) {
        // add HH:mm:ss and \n to the message
        String time = sdf.format(new Date());
        String messageLf = time + " " + message + "\n";
        // display message on console or GUI
        if (serverGUI == null)
            System.out.print(messageLf);
        else
            serverGUI.appendChat(messageLf);     // append in the room window

        // we loop in reverse order in case we would have to remove a Client
        // because it has disconnected
        for (int i = clientThreads.size(); --i >= 0; ) {

            ClientThread ct = clientThreads.get(i);

            // try to write to the Client if it fails remove it from the list
            if (!ct.sendMsg(new Message(Message.MESSAGE, messageLf))) {
                clientThreads.remove(i);
                display("Disconnected Client " + ct.userName + " removed from list.");
            }
        }
    }

    //COMMUNICATION CODE with CLIENT, one instance per client
    class ClientThread extends Thread implements Comms {
        //This is the socket accepted by the server
        Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;

        int id; //ID to identify which client
        String userName; //identify person whos using connection
        Message msg;
        String date;
        boolean keepAlive;

        ClientThread(Socket socket) {

            id = ++connectionID;
            this.socket = socket;
            System.out.println("Client " + id + " creating input/output stream");

            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                //read username to identify

                //TODO: READ CODE HERE
                msg = (Message) inputStream.readObject();
                userName = msg.getMessage();
                display(userName + " connected.");


            } catch (IOException e) {
                display("Cannot create input/output stream : " + e);
                return;
            } catch (ClassNotFoundException e) {
                display("Cannot read object, please try again : " + e);
            }
            date = new Date().toString() + "\n";
        }

        public void run() {
            keepAlive = true;
            while (keepAlive) {
                //While connection is alive, keep receiving messages
                recieveMsg();
            }
            remove(id);
            close();
        }

        @Override
        public boolean sendMsg(Message message) {
            //Check socket connected status, before sending anything
            if (!socket.isConnected()) {
                close();
                return false;
            }

            try {
                outputStream.writeObject(message);
            } catch (IOException e) {
                display("Error sending message to " + userName + " , \n" + e);
            }
            return true;
        }

        @Override
        public void recieveMsg() {
            while (keepAlive) {
                try {
                    msg = (Message) inputStream.readObject();
                } catch (IOException e) {
                    display(userName + " recieve message error : " + e);
                    break;
                } catch (ClassNotFoundException e) {
                    display("Class not found exception : " + e);
                    break;
                }


                //TODO: Add more cases here, this will interact with serverGUI later
                //switch depending on the message received
                switch (msg.getType()) {
                    case Message.MESSAGE:
                        broadcast(userName + ": " + msg.getMessage());
                        break;
                    case Message.LOGOUT:
                        display(userName + " disconnected with LOGOUT");
                        keepAlive = false;
                        break;
                    case Message.WHOSISIN:
                        sendMsg(new Message(Message.MESSAGE, "List of connected users at " + sdf.format(new Date()) + "\n"));
                        for (int i = 0; i < clientThreads.size(); ++i) {
                            ClientThread ct = clientThreads.get(i);
                            sendMsg(new Message(Message.WHOSISIN, (i + 1) + ") " + ct.userName + " since " + ct.date));
                        }
                        break;
                    case Message.REGUSER:
                        //TODO: Family name can probably split into string array
                        RegisterMessage regMsg = (RegisterMessage) msg;
                        String username = regMsg.getUsername();
                        char[] password = regMsg.getPassword();
                        //UserID is atomic integer just to be safe
                        //User object could have been created at client side, but handling it at server seems to solve multiple users
                        users.add(new User(username, username, userID.incrementAndGet(), password));
                }

            }
        }

        private void close() {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}