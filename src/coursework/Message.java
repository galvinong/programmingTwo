package coursework;


import java.io.Serializable;

/**
 * Created by Galvin on 5/14/2015.
 */
//Message class with possible subclasses for different kinds of messages
public class Message implements Serializable {

    static final int WHOSISIN = 0, MESSAGE = 1, CREATE = 2, BIDITEM = 3,
            CHECKBIDSMADE = 4, ITEMSTATUS = 5, NOTIFY = 6, REGUSER = 7, LOGOUT = 9;
    private int type;
    private String message;


    public Message(int type, String msg) {
        this.type = type;
        this.message = msg;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

}

class RegisterMessage extends Message {

    char[] password;
    String username;

    public RegisterMessage(int type, String username, char[] password) {
        super(type, username);
        this.password = password;
    }

    public char[] getPassword() {
        return password;
    }

    public String getUsername(){
        return username;
    }
}
