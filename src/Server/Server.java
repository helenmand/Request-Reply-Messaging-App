package src.Server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
    public static void main(String[] args) {
        try {
            int port_number = 0;

            if (args.length == 1) {
                port_number = Integer.parseInt(args[0]);
            }
            else {
                System.out.println("Invalid arguments");
                System.exit(0); // exit arguments have been given wrong
            }

            Functions_server stub = new Functions_server();
            Registry rmiRegistry = LocateRegistry.createRegistry(port_number);
            rmiRegistry.rebind("functions", stub);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}