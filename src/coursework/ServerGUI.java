package coursework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Galvin on 5/13/2015.
 */
public class ServerGUI extends JFrame implements ActionListener, WindowListener{

    private JButton stopStartBtn;
    private JTextArea chatArea, eventArea;
    private JTextField portNumTextField;
    private ServerComms server;

    public ServerGUI(int port) {
        super("Auction Server");
        server = null;

        //North panel contains the start/stop button and port num textfield
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Port Number : "));
        portNumTextField = new JTextField(("   " + port));
        northPanel.add(portNumTextField);
        stopStartBtn = new JButton("Start");
        stopStartBtn.addActionListener(this);
        northPanel.add(stopStartBtn);
        add(northPanel, BorderLayout.NORTH);


        //TODO: chat area not needed, just need event area
        //TODO: change the server GUI
        //Center panel contains the chat area, and event area
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        chatArea = new JTextArea(80, 80);
        chatArea.setEditable(false);
        appendChat("Chat \n");
        centerPanel.add(new JScrollPane((chatArea)));
        eventArea = new JTextArea(80, 80);
        eventArea.setEditable(false);
        appendEvent(("Events log.\n"));
        centerPanel.add((new JScrollPane(eventArea)));
        add(centerPanel);

        addWindowListener(this);
        setSize(400, 400);
        setVisible(true);
    }

    // append message to the two JTextArea
    // position at the end
    void appendChat(String str) {
        chatArea.append(str);
        chatArea.setCaretPosition(chatArea.getText().length() - 1);
    }
    void appendEvent(String str) {
        eventArea.append(str);
        eventArea.setCaretPosition(chatArea.getText().length() - 1);
    }

    //Action for the start stop server button
    public void actionPerformed(ActionEvent event) {
        //Only stop when there is a server, and allow changes to textFields
        if (server != null) {
            server.stop();
            server = null;
            portNumTextField.setEditable(true);
            stopStartBtn.setText("Start");
            return;
        }

        //Parsing port numbers for connection to server
        int port;
        try {
            port = Integer.parseInt(portNumTextField.getText().trim());
        } catch (Exception e) {
            appendEvent("Invalid port number given");
            return;
        }

        server = new ServerComms(port, this);

        //Start the server, and keep it running
        new ServerRunning().start();
        stopStartBtn.setText("Stop");
        portNumTextField.setEditable(false);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        // if my Server exist
        if(server != null) {
            try {
                server.stop();			// ask the server to close the conection
            }
            catch(Exception eClose) {
            }
            server = null;
        }
        // dispose the frame
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    //Main server thread
    class ServerRunning extends Thread {
        public void run() {
            server.start();
            stopStartBtn.setText("Start");
            portNumTextField.setEditable(true);
            appendEvent("Server crashed\n");
            server = null;
        }
    }

    public static void main(String[] arg) {
        // start server default port 1500
        new ServerGUI(1500);
    }
}
