package chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static String name = "chatApp";
    private final static int PORT = 15000;

    private static ServerSocket init() {
        ServerSocket socket;

        try {
            socket = new ServerSocket(PORT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        System.out.printf("[server]> socket created at port %d\n", PORT);

        return socket;
    }

    private static Socket initClientConnection(ServerSocket serverSocket) {
        Socket socket;

        try {
            System.out.printf("[server]> waiting for new connection... \n");
            socket = serverSocket.accept();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return socket;
    }

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket clientSocket;

        serverSocket = init();
        clientSocket = initClientConnection(serverSocket); //blocking method

        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("[server]< connection accept from client ");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (serverSocket.isBound()) {
            in.lines().forEach(e -> { //blocking method, waits until client sends something
                PrintWriter out;
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("[server]< accepted " + e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println("[server]> send success ");
                out.println("success");
                out.flush();

            });
        }

        //serverSocket.close();
    }
}
