package src.Server;

public class Message {

     private boolean isRead;
     private String sender;
     private String receiver;
     private String body;
     private int message_id;

    // constructors
    public Message(boolean isRead, String sender, String receiver, String body, int message_id){
        this.isRead = isRead;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.message_id = message_id;
    }

    public Message(){
        this.isRead = false;
        this.sender = "";
        this.receiver = "";
        this.body = "";
        this.message_id = 0;
    }

    // getters
    public String getSender(){
        return sender;
    }

    public String getReceiver(){
        return receiver;
    }

    public String getBody(){
        return body;
    }

    public int getMessage_id(){
        return message_id;
    }

    public boolean getIsRead() {
        return isRead;
    }

    // setters
    public void setiIsRead(boolean isRead){
        this.isRead = isRead;
    }

    /**
     * Creates a string containing information of the message
     * in the following formnat:
     *          'message_id. from: sender',
     * if the string is not read then a '*' is added.
     * 
     * @return the string
     */
    public String show_message_inbox(){
        String output = Integer.toString(this.message_id) +  ". from: " + this.sender; 
        if (this.isRead) { return output; }
    
        return output + "*";
    }

    /**
     * Creates a string containing information of the message
     * in the following formnat:
     *          '(sender)message_body'
     * 
     * @return the string
     */
    public String show_message_read(){
        String output = "(" + this.sender + ")" + " " + this.body;
        return output;
    }
}
