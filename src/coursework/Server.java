package coursework;


/**
 * Created by Galvin on 4/25/2015.
 */
public class Server {

    /*
     *  To run as a console application just open a console window and:
     * > java Server
     * > java Server portNumber
     * If the port number is not specified 1500 is used
     */
    public static void main(String[] args) {

        // start server on port 1500 unless a PortNumber is specified
        int portNum = 1500;
        switch (args.length) {
            case 1:
                try {
                    portNum = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Server [portNumber]");
                    return;
                }
            case 0:
                break;
            default:
                System.out.println("Usage is: > java Server [portNumber]");
                return;
        }
        // create a server object and start it
        ServerComms serverComms = new ServerComms(portNum);
        serverComms.start();
    }
}



