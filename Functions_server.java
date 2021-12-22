import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;


public class Functions_server extends UnicastRemoteObject implements Funcs {

    // properties
    private HashMap<Integer, String> users = new HashMap<Integer, String>();
    private HashMap<Integer, Message> messages = new HashMap<Integer,Message>();
    int message_counter = 0;
    //private ArrayList<Message> messages = new ArrayList<Message>();

    // methods
    public Functions_server() throws RemoteException{
        super();
    }

    private static int generate_auth_token(String username){
        return  username.hashCode();
    }

    /*
     * Function No. 1
     * Creating accounts, duplicates are not allowed.
     * Every user gets a unique token, by hashing its username.
     * Returns the auth token if the account was created successfully, otherwise it returns -1.
     * @param username the desired username
     */
    public int create_account(String username) throws  RemoteException{
        if(users.containsValue(username)){
            return -1;
        }
        else{
            int auth_token = generate_auth_token(username);
            users.put(auth_token, username);
            return auth_token;
        }
    }

    /*
     * Function No. 2
     * Returns every registered system account.
     */
    public ArrayList<String> show_accounts() throws  RemoteException{
        ArrayList<String> account_usernames = new ArrayList<>();
    
        for (Integer token: users.keySet()){
            account_usernames.add(users.get(token));
        }

        return account_usernames;
    }

    // 3
    public int send_message(int auth_token, String username, String message_body) throws  RemoteException{
        if(!users.containsValue(username)){
            return -1;
        }
        else if (!users.containsKey(auth_token)){
            return -2;
        }
        else{
            Message message = new Message(false, users.get(auth_token), username, message_body, message_counter);
            messages.put(message_counter++, message);
            return 0;
        }
    }

    // 4
    public ArrayList<Message> show_inbox(int auth_token) throws  RemoteException{
        ArrayList<Message> inbox = new ArrayList<Message>();
        for(Integer msg_id : messages.keySet()){
            if (messages.get(msg_id).getReceiver() == Integer.toString(auth_token)){
                inbox.add(messages.get(msg_id));
            }
        }
        return inbox;
    }

    // 5
    public void read_message(int auth_token, int message_id) throws  RemoteException{

    }

    // 6
    public void delete_message(int auth_token, int message_id) throws  RemoteException{

    }
}