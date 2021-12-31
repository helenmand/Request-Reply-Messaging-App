
public class Message {

     private boolean isRead;
     private String sender;
     private String receiver;
     private String body;
     private int message_id;

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

    public boolean getIsRead() {
        return isRead;
    }

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

    public void setiIsRead(boolean isRead){
        this.isRead = isRead;
    }

    public String show_message_inbox(){
        String output = Integer.toString(this.message_id) +  ". from: " + this.sender; 
        if (this.isRead) { return output; }
    
        return output + "*";
    }

    public String show_message_read(){
        String output = "(" + this.sender + ")" + " " + this.body;
        return output;
    }
}
