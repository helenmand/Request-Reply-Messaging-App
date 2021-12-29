import java.util.ArrayList;

public class Account{
    private String username;
    private int auth_token;
    private ArrayList<Message> inbox;

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public Account(){
        this.username = "";
        this.auth_token = 0;
        this.inbox = new ArrayList<Message>();
    }

    public Account(String username, int auth_token){
        this.username = username;
        this.auth_token = auth_token;
        this.inbox = new ArrayList<Message>();
    }

    public Account(String username, int auth_token, ArrayList<Message> inbox){
        this.username = username;
        this.auth_token = auth_token;
        this.inbox = new ArrayList<Message>();
        this.inbox = inbox;
    }

    public String getUsername(){
        return this.username;
    }

    public int getAuthToken(){
        return this.auth_token;
    }

    public ArrayList<Message> getInbox(){
        return this.inbox;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setAuthToken(int auth_token){
        this.auth_token = auth_token;
    }

    public void setInbox(ArrayList<Message> inbox){
        this.inbox = inbox;
    }

    public void addInboxMessage(Message message){
        /*Message msg = new Message(false, "meow", "meos", "message", 5);
        inbox.add(msg);

        for(Message msge : inbox){
            System.out.println(msge.show_message());
        }

        if(inbox.add(message)){
            inbox.add(message);
            System.out.println("Success IG?!");
        }
        else{
            System.out.println("Failed");
        }

        for(Message msge : inbox){
            System.out.println(msge.show_message());
        }*/

        this.inbox.add(message);
    }

    public int removeMessage(int message_id){
        int index = -1;
        for(Message msg : inbox){
            if(msg.getMessage_id() == message_id){
                index = inbox.indexOf(msg);
            }
        }

        if(index > -1){
            return -1;
        }
        else{
            inbox.remove(index);
            return 0;
        }
    }
}
