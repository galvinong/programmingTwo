package coursework;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Galvin on 4/23/2015.
 */
public interface Comms {
    //'sendMessage' allows each client to send a message object to the server
    // and the server to send a message to each client.
    boolean sendMsg(Message message);

    //The Server and Client applications will also need to check for incoming messages
    //by calling a 'receiveMessage' method (or methods) of the Comms class.
    void recieveMsg();

}


//
//class SocketComms implements Comms {
//    private Socket outToClient;
//    private Socket outToServer;
//    private int clientNumber;
//    ArrayList<Message> serverMessages = new ArrayList<>();
//    ArrayList<Message> clientMessages = new ArrayList<>();
//    ServerPanel accessServerPanel;
//    ClientPanel accessClientPanel;
//
//
//
//    //**CLIENT** to specify which host and port to connect to
//    public SocketComms(Socket socket, ClientPanel clientPanel) {
//        this.accessClientPanel = clientPanel;
//        this.outToServer = socket;
//        //OUTPUT to server, LISTEN FOR messages and store into arraylist
//        try {
//            BufferedReader serverOut = new BufferedReader(new InputStreamReader(outToServer.getInputStream()));
//            //TODO: PROBLEM IS HERE, stops the code from doing anything
//            while (true) {
//                Message clientMsg = new Message(serverOut.readLine());
//                clientLog(clientMsg.getMessage());
//                clientMessages.add(clientMsg);
//            }
//        } catch (IOException e) {
//            clientPanel.appendLog("Error contacting server");
//        } finally {
//            try {
//                outToServer.close();
//            } catch (IOException e) {
//                clientPanel.appendLog("Couldn't close socket");
//            }
//        }
//    }
//
//    //**SERVER** start listening to specific port
//    public SocketComms(Socket socket, int clientNumber, ServerPanel serverPanel) {
//        //This socket has already accepted the listener
//        this.outToClient = socket;
//        this.clientNumber = clientNumber;
//        this.accessServerPanel = serverPanel;
//        serverPanel.appendLog("new connection made with client #" + clientNumber + " at socket #" + socket);
//
//        //Server
//        try {
//            BufferedReader clientOut = new BufferedReader(new InputStreamReader(outToClient.getInputStream()));
//            while (true) {
//                Message serverMsg = new Message(clientOut.readLine());
//                serverLog(serverMsg.getMessage());
//                serverMessages.add(serverMsg);
//            }
//        } catch (IOException e) {
//            serverPanel.appendLog("Error handling client " + clientNumber);
//        } finally {
//            try {
//                outToClient.close();
//            } catch (IOException e) {
//                serverPanel.appendLog("Couldn't close socket");
//            }
//        }
//    }
//
//
//    @Override
//    public void sendMsg(Message message) {
//        //SERVER CODE, if message is an instanceof serverMessage, send to client
//        if (message instanceof ServerMessage) {
//            try {
//                PrintWriter out = new PrintWriter(outToClient.getOutputStream(), true);
//                //Gives a message to the client
//                out.println(((ServerMessage) message).identifyMessage().getMessage());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //
//        if(message instanceof ClientMessage){
//            try {
//                PrintWriter out = new PrintWriter(outToServer.getOutputStream(), true);
//                //Gives a message to the server
//                out.println(((ClientMessage) message).identifyMessage().getMessage());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //Receive messages, just read out from array list??, array list may not be a good idea, commands need to be executed rightaway
//    @Override
//    public void recieveMsg() {
//        for (Message msg : clientMessages) {
//            clientLog("Client message : " + msg.getMessage());
//        }
//
//        for (Message msg : serverMessages) {
//            serverLog("Server message : " + msg.getMessage());
//        }
//    }
//
//    private void serverLog(String message) {
//        accessServerPanel.logTextArea.append(message + "\n");
//    }
//
//    private void clientLog(String message) {
//        accessClientPanel.logTextArea.append(message + "\n");
//    }
//
//}
//
//
//class ServerMessage extends Message {
//
//    public ServerMessage(String msg) {
//        super(msg);
//    }
//
//    public Message identifyMessage() {
//        return new Message("Hello you are client");
//    }
//}
//
//class ClientMessage extends Message{
//
//    public ClientMessage(String msg) {
//        super(msg);
//    }
//
//    public Message identifyMessage() {
//        return new Message("Hi im client");
//    }
//
//}
