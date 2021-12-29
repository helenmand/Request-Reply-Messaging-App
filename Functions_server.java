import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;


public class Functions_server extends UnicastRemoteObject implements Funcs {

    // properties
    //private HashMap<Integer, String> users = new HashMap<Integer, String>();
    private HashMap<Integer, Message> messages = new HashMap<Integer,Message>();
    int message_counter = 0;
    private ArrayList<Account> usersa = new ArrayList<Account>();
    private HashMap<Integer, Account> users = new HashMap<Integer, Account>();

    //private ArrayList<Message> messages = new ArrayList<Message>();

    // methods
    public Functions_server() throws RemoteException{
        super();
    }

    private static int generate_auth_token(String username){
        return  username.hashCode();
    }

    /*private Account findAccount(int auth_token){
        for (Account user : usersa){
            if(user.getAuthToken() == auth_token) { return user; }
        }
    }*/

    public int check_username(String username){
        for (Account user : usersa){
            if (user.getUsername().equals(username)){ return -1; }
        }
        return 0;
    }

    public int check_auth_token(int auth_token){
        for (Account user : usersa){
            if (user.getAuthToken() == auth_token){ return -1; }
        }
        return 0;
    }

    public int find_message(int message_id, ArrayList<Message> inbox){
        int index = -1;
        for (Message msg : inbox){
            if(msg.getMessage_id() == message_id){
                index = inbox.indexOf(msg);
            }
        }
        return index;
    }

    /*
     * Function No. 1
     * Creating accounts, duplicates are not allowed.
     * Every user gets a unique token, by hashing its username.
     * Returns the auth token if the account was created successfully, otherwise it returns -1.
     * @param username the desired username
     */
    public int create_account(String username) throws  RemoteException{
        for (Account user : usersa){
            if (user.getUsername().equals(username)){ return -1; }
        }
        
        int auth_token = generate_auth_token(username);
        Account user = new Account(username, auth_token);
        users.put(auth_token, user);
        return auth_token;
    }

    /*
     * Function No. 2
     * Returns every registered system account.
     */
    public ArrayList<String> show_accounts() throws  RemoteException{
        ArrayList<String> account_usernames = new ArrayList<>();
    
        for (Integer token: users.keySet()){
            account_usernames.add(users.get(token).getUsername());
        }

        return account_usernames;
    }

    // 3
    public int send_message(int auth_token, String username, String message_body) throws  RemoteException{
        Message message = new Message(false, users.get(auth_token).getUsername(), username, message_body, message_counter++);
        int auth_tkn = -1;
        
        for (Integer token : users.keySet()){
            if(users.get(token).getUsername().equals(username)){
                auth_tkn = token;    
            }
        }

        Account usr = new Account(users.get(auth_tkn).getUsername(), users.get(auth_tkn).getAuthToken(), users.get(auth_tkn).getInbox());
        usr.addInboxMessage(message);

        users.remove(auth_tkn);
        users.put(auth_tkn, usr);
    
        return 0;
    }

    // 4
    public ArrayList<String> show_inbox(int auth_token) throws RemoteException{
        ArrayList<String> inbox = new ArrayList<>();
        ArrayList<Message> user_inbox = new ArrayList<Message>();
        
        user_inbox = users.get(auth_token).getInbox();

        for (Message msg : user_inbox){
            inbox.add(msg.show_message_inbox());
        }
        
        return inbox;
    }

    // 5
    public String read_message(int auth_token, int message_id) throws RemoteException{
        ArrayList<Message> user_inbox = new ArrayList<Message>();
        user_inbox = users.get(auth_token).getInbox();

        int index = find_message(message_id, user_inbox);
        if (index > -1){
            Message updated_message = new Message(true, user_inbox.get(index).getSender(), user_inbox.get(index).getReceiver(), user_inbox.get(index).getBody(), user_inbox.get(index).getMessage_id());
            user_inbox.remove(index);
            
            Account usr = new Account(users.get(auth_token).getUsername(), users.get(auth_token).getAuthToken(), user_inbox);
            usr.addInboxMessage(updated_message);

            users.remove(auth_token);
            users.put(auth_token, usr);
            return updated_message.show_message_read();
        }
        else{
            return "Message ID does not exist";
        }

        
        
    }

    // 6
    public String delete_message(int auth_token, int message_id) throws RemoteException{
        ArrayList<Message> user_inbox = new ArrayList<Message>();
        user_inbox = users.get(auth_token).getInbox();

        int index = find_message(message_id, user_inbox);
        if (index > -1){
            user_inbox.remove(index);
            Account usr = new Account(users.get(auth_token).getUsername(), users.get(auth_token).getAuthToken(), user_inbox);

            users.remove(auth_token);
            users.put(auth_token, usr);
            return "OK";
        }
        else{
            return "Message does not exist";
        }
    }
}