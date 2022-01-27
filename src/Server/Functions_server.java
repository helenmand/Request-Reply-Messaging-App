package src.Server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import src.Common.Funcs;


public class Functions_server extends UnicastRemoteObject implements Funcs {

    // attributes
    int message_counter = 0;
    private HashMap<Integer, Account> users = new HashMap<Integer, Account>();

    // methods
    public Functions_server() throws RemoteException{
        super();
    }

    /**
     * Checks if a string is valid format.
     * Only latin characters, numbers and underscores are allowed.
     * Must contain at least one character to be valid.
     * 
     * @param s the given string
     * @return if the string s is valid
     */
    private static boolean is_valid_username(String s) {
        return s != null && s.matches("^[a-zA-Z0-9_]+$");
    }

    /**
     * Generates a random number four digit number
     * between 1000 and 9999. Repeats if the number
     * belongs to an existing user.
     * 
     * @return a unique four digit number, the authentication token.
     */
    private int generate_unique_auth_token(){
        Random rand = new Random();
        int token = 1000 + rand.nextInt(9000);

        while(users.containsKey(token)){
            token = 1000 + rand.nextInt(9000);
        }

        return token;
    }

    /**
     * Finds a message with a specific id in an inbox
     * 
     * @param message_id the id of the message
     * @param inbox to be searched
     * @return -1 if the message does not exist, 
     *         the index of the message in the arrat if it does
     */
    private int find_message(int message_id, ArrayList<Message> inbox){
        int index = -1;
        
        for (Message msg : inbox){
            if(msg.getMessage_id() == message_id){ index = inbox.indexOf(msg); }
        }
        return index;
    }

    /**
     * Checks if the given username exists already
     * 
     * @param username given username
     * @return -1 if the username exists, 0 if it does not
     */
    public int check_username(String username) throws RemoteException{
        for (Integer auth_token : users.keySet()){
            if (users.get(auth_token).getUsername().equals(username)){ return -1; }
        }
        return 0;
    }

    /**
     * Checks if the given auth token exists already
     * 
     * @param auth_token given auth token
     *  @return -1 if the auth token exists, 0 if it does not
     */
    public int check_auth_token(int auth_token) throws RemoteException{
        if (users.containsKey(auth_token)){ return -1; }
        return 0;
    }

    /**
     * Function ID No. 1
     * 
     * Creating accounts, duplicates are not allowed.
     * Username must be alphanumeric.
     * Every user gets a unique token, by hashing its username.
     * 
     * @param username the desired username
     * @return auth token if the account was created successfully otherwise 
     *         -1, which means the given username is taken.
     */
    public int create_account(String username) throws  RemoteException{
        if (!is_valid_username(username)){
            return -2;
        }
        else if(users.size() == 9000){
            return -3;
        }
        else if(check_username(username) == 0){
            int auth_token = generate_unique_auth_token();
            Account user = new Account(username, auth_token);
            users.put(auth_token, user);
            
            return auth_token;
        }
        else{ return -1; }
        
        
    }

    /**
     * Function ID No. 2
     * 
     * @return every registered system account.
     */
    public ArrayList<String> show_accounts() throws  RemoteException{
        ArrayList<String> account_usernames = new ArrayList<>();
    
        for (Integer token: users.keySet()){ account_usernames.add(users.get(token).getUsername()); }

        return account_usernames;
    }

    /**
     * Function ID No. 3
     * 
     * Sends a message from a user of the system to another.
     * Allows "" (empty) messages.
     * Every message has a unique message id.
     * 
     * @param auth_token identifyer of the sender
     * @param usernamer identifyer of the receiver
     * @param message_body the message 
     */
    public void send_message(int auth_token, String username, String message_body) throws  RemoteException{
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
    }

    /**
     * Function ID No. 4
     * 
     * Return a string for every message in a specific user's inbox.
     * The messages are in the following format :
     *          'message_id. from: sender',
     * followed by '*' in case the message is not read yet.
     * In case of an empty inbox, it returns an empty array list.
     * 
     * @param auth_token the user that makes the request.
     * @return an array list containing the users inbox.
     */
    public ArrayList<String> show_inbox(int auth_token) throws RemoteException{
        ArrayList<String> inbox = new ArrayList<>();
        
        ArrayList<Message> user_inbox = new ArrayList<Message>();
        user_inbox = users.get(auth_token).getInbox();

        for (Message msg : user_inbox){ inbox.add(msg.show_message_inbox()); }
        
        return inbox;
    }

    /**
     * Function ID No. 5
     * 
     * Marks a message, if it exists, as seen.
     * A user can read only messages in his inbox.
     * If the message does not exist in his inbox it returns 
     * a "Message ID does not exist" message, otherwise it
     * returns the message in the following format:
     *              '(sender)message_body'
     *  
     * @param auth_token user's auth token
     * @param message_id of the message to be read
     * @return the string to print for the user
     */
    public String read_message(int auth_token, int message_id) throws RemoteException{
        ArrayList<Message> user_inbox = new ArrayList<Message>();
        user_inbox = users.get(auth_token).getInbox();

        int index = find_message(message_id, user_inbox);
        if (index > -1){
            Message updated_message = new Message(true, user_inbox.get(index).getSender(), user_inbox.get(index).getReceiver(), user_inbox.get(index).getBody(), user_inbox.get(index).getMessage_id());
            Account usr = new Account(users.get(auth_token).getUsername(), users.get(auth_token).getAuthToken(), user_inbox);
            
            user_inbox.remove(index);
            usr.addInboxMessage(updated_message);

            users.remove(auth_token);
            users.put(auth_token, usr);
            return updated_message.show_message_read();
        }
        else{
            return "Message ID does not exist";
        }

        
        
    }

    /**
     * Function ID No. 6
     * 
     * Deletes a message from a users inbox, only 
     * if the message exists in his inbox.
     * 
     * @param auth_token user's auth token
     * @param message_id the message id of the message the user wants to delete
     * @return 'OK', if the message deleted successfuly, 
     *          "Message does not exist", if the message does not exist
     */
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