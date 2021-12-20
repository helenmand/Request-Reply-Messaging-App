import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class Functions_server extends UnicastRemoteObject implements Funcs {

    // properties
    private HashMap<Integer, String> users = new HashMap<Integer, String>();
    private HashMap<Integer, Message> messages = new HashMap<Integer,Message>();
    //private ArrayList<Message> messages = new ArrayList<Message>();

    // methods
    public Functions_server() throws RemoteException{
        super();
    }

    private static int generate_auth_token(String username){
        return  username.hashCode() % 10000;
    }

    /**
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

    /**
     * Function No. 2
     * Prints every registered system account.
     */
    public void show_accounts() throws  RemoteException{
        for (Integer token: users.keySet()){
            System.out.println(users.get(token));
        }
    }

    // 3
    public int send_message(int auth_token, String username, String message_body) throws  RemoteException{
        if(!users.containsValue(username)){
            return -1;
        }
        else{
            return 0;
        }
    }

    // 4
    public void show_inbox(int auth_token) throws  RemoteException{

    }

    // 5
    public void read_message(int auth_token, int message_id) throws  RemoteException{

    }

    // 6
    public void delete_message(int auth_token, int message_id) throws  RemoteException{

    }
}