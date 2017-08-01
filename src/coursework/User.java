package coursework;

/**
 * Created by Galvin on 4/23/2015.
 */
public class User {
    //Users must have a given name, family name, unique user id
    private String name, familyName;
    private int userID;


    private char[] password;

    public User(String name, String familyName, int userID, char[] password){
        this.name = name;
        this.familyName = familyName;
        this.userID = userID;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getUserID() {
        return userID;
    }

    public char[] getPassword() {
        return password;
    }

//Initially register with a password of their choosing


}