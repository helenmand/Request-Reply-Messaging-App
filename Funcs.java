import java.rmi.*;
import java.util.ArrayList;

public interface Funcs extends Remote {
    // 1
    public int create_account(String username) throws  RemoteException;

    // 2
    public ArrayList<String> show_accounts() throws  RemoteException;

    // 3
    public int send_message(int auth_token, String username, String message_body) throws  RemoteException;

    // 4
    public ArrayList<String> show_inbox(int auth_token) throws  RemoteException;

    // 5
    public String read_message(int auth_token, int message_id) throws  RemoteException;

    // 6
    public String delete_message(int auth_token, int message_id) throws  RemoteException;

    // assistive functions
    public int check_auth_token(int auth_token) throws RemoteException;
    public int check_username(String username) throws RemoteException;

}