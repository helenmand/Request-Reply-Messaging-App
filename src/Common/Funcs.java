package src.Common;
import java.rmi.*;
import java.util.ArrayList;

public interface Funcs extends Remote {
    // FN ID 1
    public int create_account(String username) throws  RemoteException;

    // FN ID 2
    public ArrayList<String> show_accounts() throws  RemoteException;

    // FN ID 3
    public void send_message(int auth_token, String username, String message_body) throws  RemoteException;

    // FN ID 4
    public ArrayList<String> show_inbox(int auth_token) throws  RemoteException;

    // FN ID 5
    public String read_message(int auth_token, int message_id) throws  RemoteException;

    // FN ID 6
    public String delete_message(int auth_token, int message_id) throws  RemoteException;

    // assistive functions
    public int check_auth_token(int auth_token) throws RemoteException;
    public int check_username(String username) throws RemoteException;

}