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
            // Default, 0: IP, 1: port number, 2: function id.
            // depends on the FN ID, 3: auth token/ username, 4: recipient/ message id
            // and final 5: message body.
            if (args.length >= 4) {
                arguments = Arrays.asList(args);
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
                    stub.show_accounts();
                    break;
                case "3" :
                    int state = stub.send_message(Integer.parseInt(arguments.get(3)), arguments.get(4), arguments.get(5));

                    if (state == 0){
                        System.out.println("OK");
                    }
                    else{
                        System.out.println("User does not exist");
                    }
                    break;
                case "4" :
                    stub.show_inbox(Integer.parseInt(arguments.get(3)));
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