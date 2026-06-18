import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server extends UnicastRemoteObject implements Interface {
    public Server() throws RemoteException {
        super();
    }

    @Override
    public String SendMessage(String Name) throws RemoteException {
        System.out.println("Client Send this Message : " + Name);
        return "The Server show this Message " + Name;
    }

    public static void main(String[] args){
        try {
            Registry registry = LocateRegistry.createRegistry(1333);
            Server serv = new Server();
            registry.rebind("ServiceName" , serv);
            System.out.println("The Server is ready");
        }
        catch (Exception ex){
            System.err.println("Server Error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

