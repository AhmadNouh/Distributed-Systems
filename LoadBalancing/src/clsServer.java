import java.io.*;
import java.net.*;

public class clsServer {
    private final String ServerName;
    private final int Port;

    public clsServer(String ServerName , int Port){
        this.ServerName = ServerName;
        this.Port = Port;
    }

    public void start(){
        new Thread( () -> {
            try (ServerSocket servSocket = new ServerSocket(Port)){
                System.out.println("{ " + ServerName + " } " + "Using this Port : " + Port);

                while (true){
                    Socket socket = servSocket.accept();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream() , true);

                    String request = reader.readLine();
                    System.out.println("{ " + ServerName + " } get request " + request);

                    writer.println(" { " + ServerName + " } finished the request " + request);

                    socket.close();
                }
            }
            catch (IOException ex){
                System.err.println("Server Error in { " + ServerName + " } " + ex.getMessage());
            }
        }).start();
    }

    public static void main(String[] args){

        clsServer ser1 = new clsServer("Server_1" , 1222);
        ser1.start();

        clsServer ser2 = new clsServer("Server_2" , 1333);
        ser2.start();

        clsServer ser3 = new clsServer("Server_3" , 1444);
        ser3.start();
    }
}
