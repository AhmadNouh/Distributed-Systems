import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Client {
    public static void main(String[] args){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost" , 1333);

            Interface search = (Interface) registry.lookup("ServiceName");

            String servResponse = search.SendMessage("I am Client");

            System.out.println("Server Response : " + servResponse);
        }
        catch (Exception ex){
            System.err.println("Client Side Error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
