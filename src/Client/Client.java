package src.Client;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.Common.Funcs;

// To change the 2 input parameters (double numbers) in Intellij go to: Edit Configurations -> Client -> Built and Run

public class Client {
    public static void main(String[] args) {
        try {
            List<String> arguments = new ArrayList<>();
            //String message_body = "";
            // Default, 0: IP, 1: port number, 2: function id.
            // depends on the FN ID, 3: auth token/ username, 4: recipient/ message id
            // 5: message body
            if (args.length >= 4) {
                if (Integer.parseInt(args[2]) >= 1 && Integer.parseInt(args[2]) <= 6){
                    arguments = Arrays.asList(args);
                } else {
                    System.out.println("Invalid function ID");
                    System.exit(0);
                }
            }
            else {
                System.out.println("Invalid arguments");
                System.exit(0); // exit if not all arguments have been given
            }

            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(Integer.parseInt(arguments.get(1)));
            // get reference for remote object
            Funcs stub = (Funcs) rmiRegistry.lookup("functions");

            // checks if the given auth token is valid
            if (arguments.get(2).equals("2") || arguments.get(2).equals("3") 
             || arguments.get(2).equals("4") || arguments.get(2).equals("5") || arguments.get(2).equals("6")){
                int auth_token_pass = stub.check_auth_token(Integer.parseInt(arguments.get(3)));
                if (auth_token_pass == 0) {
                    System.out.println("Invalid Auth Token");
                    System.exit(0);
                }
            }

            // checks if the username given is valid
            if(arguments.get(2).equals("3")){
                int user_name_pass = stub.check_username(arguments.get(4));
                if (user_name_pass == 0){
                    System.out.println("User does not exist");
                    System.exit(0);
                }
            }

            // according to the FN ID the appropriate function is executed
            switch (arguments.get(2)) {
                // Function ID 1 : creating an account.
                case "1":
                    int auth_token = stub.create_account(arguments.get(3));
                    if (auth_token == -1) {
                        System.out.println("Sorry, the user already exists.");
                    }
                    else if (auth_token == -2 ){
                        System.out.println("Invalid username");
                    }
                    else if (auth_token == -3){
                        System.out.println("Sorry, unable to create new accounts.");
                    } 
                    else {
                        System.out.println(auth_token);
                    }
                    break;
                // Function ID 2 : printing all accounts.
                case "2" :
                    ArrayList<String> usernames = new ArrayList<>();
                    usernames = stub.show_accounts();
                    
                    for (String username : usernames){
                        System.out.println(username);
                    }
                    break;
                // Function ID 3 : sending a message. 
                case "3" :
                    stub.send_message(Integer.parseInt(arguments.get(3)), arguments.get(4), arguments.get(5));
                    System.out.println("OK");
                    
                    break;
                // Function ID 4: printing the message box of a specific user
                case "4" :
                    ArrayList<String> inbox = new ArrayList<>();
                    inbox = stub.show_inbox(Integer.parseInt(arguments.get(3)));
                    for (String msg : inbox){ System.out.println(msg); }
                    
                    break;
                // Function ID 5 : reading a message
                case "5" :
                    System.out.println(stub.read_message(Integer.parseInt(arguments.get(3)), Integer.parseInt(arguments.get(4))));
                
                    break;
                // Function ID 6 : deleting a message from a user's message box.
                case "6" :
                    System.out.println(stub.delete_message(Integer.parseInt(arguments.get(3)), Integer.parseInt(arguments.get(4))));
                    
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}