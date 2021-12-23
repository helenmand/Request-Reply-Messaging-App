import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// To change the 2 input parameters (double numbers) in Intellij go to: Edit Configurations -> Client -> Built and Run


public class Client {
    public static void main(String[] args) {
        try {
            List<String> arguments = new ArrayList<>();
            String message_body = "";
            // Default, 0: IP, 1: port number, 2: function id.
            // depends on the FN ID, 3: auth token/ username, 4: recipient/ message id
            if (args.length >= 4) {
                arguments = Arrays.asList(args);
                if (args[2] == "3"){
                    for(int i=5; i < args.length; i++){
                        message_body += args[i];
                    }
                }
            }
            else {
                System.exit(0); // exit if not all arguments have been given
            }

            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(Integer.parseInt(arguments.get(1)));
            // get reference for remote object
            Funcs stub = (Funcs) rmiRegistry.lookup("functions");

            switch (arguments.get(2)) {
                case "1":
                    int auth_token = stub.create_account(arguments.get(3));
                    if (auth_token == -1) {
                        System.out.println("Sorry, the user already exists.");
                    } else {
                        System.out.println(auth_token);
                    }
                    break;
                case "2" :
                    ArrayList<String> usernames = new ArrayList<>();
                    usernames = stub.show_accounts();
                    
                    for (String username : usernames){
                        System.out.println(username);
                    }
                    break;
                case "3" :
                    int state = stub.send_message(Integer.parseInt(arguments.get(3)), arguments.get(4), message_body);

                    if (state == 0){
                        System.out.println("OK");
                    }
                    else if (state == -1){
                        System.out.println("User does not exist");
                    }
                    else{
                        System.out.println("Invalid Auth Token");
                    }
                    break;
                case "4" :
                    ArrayList<Message> inbox = new ArrayList<Message>();

                    inbox = stub.show_inbox(Integer.parseInt(arguments.get(3)));
                    
                    for (Message msg : inbox){
                        System.out.println(msg.show_message(msg));
                    }

                    break;
                case "5" :
                    stub.read_message(Integer.parseInt(arguments.get(3)), Integer.parseInt(arguments.get(4)));
                    break;
                case "6" :
                    stub.delete_message(Integer.parseInt(arguments.get(3)), Integer.parseInt(arguments.get(4)));
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}