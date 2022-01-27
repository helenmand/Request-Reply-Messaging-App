package src.Server;
import java.util.ArrayList;

public class Account{
    private String username;
    private int auth_token;
    private ArrayList<Message> messageBox;

    // constructors
    public Account(){
        this.username = "";
        this.auth_token = 0;
        this.messageBox = new ArrayList<Message>();
    }

    public Account(String username, int auth_token){
        this.username = username;
        this.auth_token = auth_token;
        this.messageBox = new ArrayList<Message>();
    }

    public Account(String username, int auth_token, ArrayList<Message> messageBox){
        this.username = username;
        this.auth_token = auth_token;
        this.messageBox = new ArrayList<Message>();
        this.messageBox = messageBox;
    }

    // setter and getters
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getAuthToken(){
        return this.auth_token;
    }

    public void setAuthToken(int auth_token){
        this.auth_token = auth_token;
    }
    
    public ArrayList<Message> getInbox(){
        return this.messageBox;
    }

    public void setInbox(ArrayList<Message> messageBox){
        this.messageBox = messageBox;
    }

    /**
     * adds a message in the user's inbox.
     * 
     * @param message the message
     */
    public void addInboxMessage(Message message){
        this.messageBox.add(message);
    }

    /**
     * Searches for a message with a specific message id in 
     * the message box. Returns the index of the message in the 
     * message box, if the message exists or -1 if it does not.
     * 
     * @param message_id the message id of the message 
     * @return index of the message or -1
     */
    public int removeMessage(int message_id){
        int index = -1;
        for(Message msg : messageBox){
            if(msg.getMessage_id() == message_id){
                index = messageBox.indexOf(msg);
            }
        }

        if(index > -1){
            return -1;
        }
        else{
            messageBox.remove(index);
            return 0;
        }
    }
}
