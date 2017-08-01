package coursework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Galvin on 5/14/2015.
 */
public class ClientComms{
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;

    private ClientGUI clientGUI;

    private String server, userName;
    private int port;
    private SimpleDateFormat sdf;

    public ClientComms(String server, int port, String userName) {
        this(server, port, userName, null);
    }

    public ClientComms(String server, int port, String userName, ClientGUI cg) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.clientGUI = cg;
        sdf = new SimpleDateFormat("HH:mm:ss");
    }


    //Start connection to server
    public boolean start() {
        //connection to server
        try {
            socket = new Socket(server, port);
        } catch (IOException e) {
            display("Error, cannot connect to server : " + e);
            return false;
        }

        //If accepted, print out details
        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            display("Error creating streams : " + e);
            return false;
        }

        new listenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try
        {
            outputStream.writeObject(new Message(Message.MESSAGE, userName));
        }
        catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // success we inform the caller that it worked
        return true;

    }


    private void display(String msg) {
        String formatMessage = sdf.format(new Date()) + " " + msg;
        if (clientGUI == null)
            System.out.println(formatMessage);
        else
            clientGUI.append(formatMessage + "\n");
    }

    public void disconnect() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {

        }

        if (clientGUI != null)
            clientGUI.connectionFailed();
    }


    public boolean sendMsg(Message message) {
        try {
            outputStream.writeObject(message);
            return true;
        } catch (IOException e) {
            display("Exception sending to server : " + e);
            return false;
        }
    }

    //This thread simply waits for messages from the server and append to JTextArea
    class listenFromServer extends Thread {

        public void run() {
            recieveMsg();
        }

        //Not used, because cannot access it from here
        public void recieveMsg() {
            while (true) {
                try {
                    Message message = (Message) inputStream.readObject();

                    if (clientGUI == null) {
                        System.out.println(message.getMessage());
                        System.out.print("> ");
                    } else {
                        clientGUI.append(message.getMessage());
                    }
                } catch (IOException e) {
                    display("Server has closed the connection : " + e);
                    if (clientGUI != null) {
                        clientGUI.connectionFailed();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}