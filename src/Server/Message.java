// assistant class
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
}
