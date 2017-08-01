package coursework;

import sun.security.util.Password;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Galvin on 5/13/2015.
 */
public class ClientGUI extends JFrame implements ActionListener{

    private JLabel jLabel;
    //TextFields for input
    private JTextField userField;
    private JTextField serverField, portField;
    private JTextArea chatArea;
    private JPasswordField passwordField;

    //Buttons
    private JButton loginBtn, logoutBtn ,whoIsIn , registerBtn, connectBtn;

    private JButton createItemBtn, bidItemBtn;

    //Connection variables
    private boolean connected;
    private ClientComms client;
    private int defaultPort;
    private String defaultHost;


    public ClientGUI(String host, int portNum) {
        super("Auction Client");
        defaultPort = portNum;
        defaultHost = host;

        //North Panel
        JPanel northPanel = new JPanel(new GridLayout(4, 1));
        serverField = new JTextField(host);
        portField = new JTextField("" + portNum);
        portField.setHorizontalAlignment(SwingConstants.RIGHT);

        //Server and port address panel
        JPanel serverPortPanel = new JPanel(new GridLayout(1, 5, 1, 3));
        serverPortPanel.add(new JLabel("Server Address : "));
        serverPortPanel.add(serverField);
        serverPortPanel.add(new JLabel("Port Number : "));
        serverPortPanel.add(portField);
        connectBtn = new JButton("Connect");
        connectBtn.addActionListener(this);
        serverPortPanel.add(connectBtn);
        //Add to the north panel
        northPanel.add(serverPortPanel);

        //Label panel
        JPanel labelPanel = new JPanel(new GridLayout(1, 1));
        jLabel = new JLabel("Enter your username and password below to begin", SwingConstants.CENTER);
        labelPanel.add(jLabel);
        northPanel.add(labelPanel);

        //Username and password Panel
        JPanel loginPanel = new JPanel(new GridLayout(1, 2));
        userField = new JTextField("Anonymous");
        userField.setBackground(Color.WHITE);
        loginPanel.add(userField);
        passwordField = new JPasswordField(10);
        loginPanel.add(passwordField);
        northPanel.add(loginPanel);

        //Register, Login panel
        JPanel btnPanel = new JPanel(new GridLayout(1, 3));
        registerBtn = new JButton("Register");
        registerBtn.addActionListener(this);

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);

        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(this);
        logoutBtn.setEnabled(false);		// you have to login before being able to logout

        btnPanel.add(registerBtn);
        btnPanel.add(loginBtn);
        btnPanel.add(logoutBtn);

        northPanel.add(btnPanel);

        //Add North Panel
        add(northPanel, BorderLayout.NORTH);

        //Center panel
        chatArea = new JTextArea("Welcome to Auction System Alpha\n", 80, 80);
        JPanel centerPanel = new JPanel(new GridLayout(1,1));
        centerPanel.add(new JScrollPane(chatArea));
        chatArea.setEditable(false);
        add(centerPanel, BorderLayout.CENTER);

        // the 3 buttons

        whoIsIn = new JButton("Who is in");
        whoIsIn.addActionListener(this);
        whoIsIn.setEnabled(false);		// you have to login before being able to Who is in

        //TODO: only can bid when logged in
        createItemBtn = new JButton("Item List");
        createItemBtn.addActionListener(this);
        bidItemBtn = new JButton("Start bidding");
        bidItemBtn.addActionListener(this);

        JPanel southPanel = new JPanel();
        southPanel.add(whoIsIn);
        southPanel.add(createItemBtn);
        southPanel.add(bidItemBtn);
        add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        userField.requestFocus();
    }

    // called by the Client to append text in the TextArea
    void append(String str) {
        chatArea.append(str);
        chatArea.setCaretPosition(userField.getText().length());
    }

    // called by the GUI is the connection failed
    // we reset our buttons, label, textfield
    void connectionFailed() {
        loginBtn.setEnabled(true);
        logoutBtn.setEnabled(false);
        whoIsIn.setEnabled(false);
        jLabel.setText("Enter your username below");
        userField.setText("Anonymous");
        // reset port number and host name as a construction time
        portField.setText("" + defaultPort);
        serverField.setText(defaultHost);
        // let the user change them
        serverField.setEditable(false);
        portField.setEditable(false);
        // don't react to a <CR> after the username
        userField.removeActionListener(this);
        connected = false;
    }

    /*
    * Button or JTextField clicked
    */
    public void actionPerformed(ActionEvent e) {
        //Check with button pressed
        Object buttonPressed = e.getSource();

        //REGISTER BUTTON
        if (buttonPressed == registerBtn) {
            String userName = userField.getText();
            char[] password = passwordField.getPassword();
            client.sendMsg(new RegisterMessage(Message.REGUSER, userName, password));
        }

        //CREATE ITEM BUTTON
        if (buttonPressed == createItemBtn) {

        }

        //BID ITEM BUTTON
        if (buttonPressed == bidItemBtn) {

        }

        // ok it is coming from the JTextField
        if(connected) {
            // just have to send the message
            client.sendMsg(new Message(Message.MESSAGE, userField.getText()));
            userField.setText("");
            return;
        }


        //LOGIN BUTTON
        if(buttonPressed == loginBtn) {
            // ok it is a connection request
            String username = userField.getText().trim();

            // empty username ignore it
            if(username.length() == 0)
                return;
            // empty serverAddress ignore it
            String server = serverField.getText().trim();
            if(server.length() == 0)
                return;
            // empty or invalid port numer, ignore it
            String portNumber = portField.getText().trim();
            if(portNumber.length() == 0)
                return;
            int port = 0;
            try {
                port = Integer.parseInt(portNumber);
            }
            catch(Exception en) {
                return;   // nothing I can do if port number is not valid
            }

            // try creating a new Client with GUI
            client = new ClientComms(server, port, username, this);
            // test if we can start the Client
            if(!client.start())
                return;
            userField.setText("");
            jLabel.setText("Enter your message below");
            connected = true;

            // disable login button
            loginBtn.setEnabled(false);
            // enable the 2 buttons
            logoutBtn.setEnabled(true);
            whoIsIn.setEnabled(true);
            // disable the Server and Port JTextField
            serverField.setEditable(false);
            portField.setEditable(false);
            // Action listener for when the user enter a message
            userField.addActionListener(this);
        }

        //LOGOUT BUTTON
        if(buttonPressed == logoutBtn) {
            client.sendMsg(new Message(Message.LOGOUT, ""));
            logoutBtn.setEnabled(false);
            loginBtn.setEnabled(true);
            return;
        }


        //WHOS IS BUTTON
        if(buttonPressed == whoIsIn) {
            client.sendMsg(new Message(Message.WHOSISIN, ""));
            return;
        }

    }

    // to start the whole thing the server
    public static void main(String[] args) {
        new ClientGUI("localhost", 1500);
    }
}
