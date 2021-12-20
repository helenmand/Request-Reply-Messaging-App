import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            int port_number = Integer.parseInt(args[0]);

            Registry rmiRegistry = LocateRegistry.createRegistry(port_number);
            Funcs stub = (Funcs) rmiRegistry.lookup("functions");
            rmiRegistry.rebind("functions", stub);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}