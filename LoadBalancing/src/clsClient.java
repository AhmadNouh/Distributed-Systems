import java.io.*;
import java.net.*;
import java.util.Scanner;

public class clsClient {
    public static void main(String[] args) {
        System.out.println("The Client is ready");
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.print("Enter any message you want to send it to Load Balancer : ");
                String message = scanner.nextLine();

                if ("exit".equalsIgnoreCase(message)) break;

                Socket socket = new Socket("localhost", 8080);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                writer.println(message);

                String response = reader.readLine();
                System.out.println("This response from Server : " + response + "\n");

                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Load Balancer Error : " + e.getMessage());
        }
    }
}