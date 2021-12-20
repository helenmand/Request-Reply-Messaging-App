import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
    public static void main(String[] args) {
        try {
            int port_number = Integer.parseInt(args[0]);
            Functions_server stub = new Functions_server();

            Registry rmiRegistry = LocateRegistry.createRegistry(port_number);
        
            rmiRegistry.rebind("functions", stub);

            System.out.println("I work!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}