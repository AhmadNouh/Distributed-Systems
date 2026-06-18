import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interface extends Remote {
    public String SendMessage(String message) throws RemoteException;
}
