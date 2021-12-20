import java.rmi.*;

// interface
public interface Funcs extends Remote {
    // 1
    public int create_account(String username) throws  RemoteException;

    // 2
    public void show_accounts() throws  RemoteException;

    // 3
    public int send_message(int auth_token, String username, String message_body) throws  RemoteException;

    // 4
    public void show_inbox(int auth_token) throws  RemoteException;

    // 5
    public void read_message(int auth_token, int message_id) throws  RemoteException;

    // 6
    public void delete_message(int auth_token, int message_id) throws  RemoteException;

}