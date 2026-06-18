package Load_Balancer;

import java.io.*;
import java.net.*;
import java.util.*;

public class LoadBalancer {
    private static final int Port = 8080;
    private static final List<Integer> ServersList = Arrays.asList(1222 , 1333 , 1444);
    private static int counter = 0;

//    private static final List<Integer> WeightedServersPool = new ArrayList<>();
//
//    static {
//        int weight1 = 3;
//        int weight2 = 1;
//        int weight3 = 1;
//
//        for (int i = 0; i < weight1; i++) WeightedServersPool.add(1222);
//        for (int i = 0; i < weight2; i++) WeightedServersPool.add(1333);
//        for (int i = 0; i < weight3; i++) WeightedServersPool.add(1444);
//    }

    public static void main(String[] args) {
        try (ServerSocket servSocket = new ServerSocket(Port)) {
            System.out.println("Load Balancer Working on Port : " + Port);

            while (true) {

                Socket clientSocket = servSocket.accept();

                new Thread(() -> handleClientRequest(clientSocket)).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static synchronized int getNextServerPort() {
        int newPort = ServersList.get(counter % ServersList.size());
        counter++;
        return newPort;
    }

    private static void handleClientRequest(Socket clientSocket) {
        try {
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage = clientReader.readLine();

            int newServerPort = getNextServerPort();

            System.out.println("[ Load Balancer ] send the request of this Server Port " + newServerPort);

            try (Socket serSocket = new Socket("localhost", newServerPort);
                 PrintWriter serWriter = new PrintWriter(serSocket.getOutputStream(), true);
                 BufferedReader serReader = new BufferedReader(new InputStreamReader(serSocket.getInputStream()))) {

                serWriter.println(clientMessage);

                String backendResponse = serReader.readLine();

                clientWriter.println(backendResponse);
            }

            clientSocket.close();
        } catch (IOException e) {
            System.err.println("s " + e.getMessage());
        }

    }
}
