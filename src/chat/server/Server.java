package chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server implements Runnable {

    private final String PRETTY_NAME = "terminal chatapp";
    private final String HOST = "localhost";
    private final int PORT = 15000;

    private final List<Thread> connectionThreadList = new LinkedList<>();
    private final List<Connection> connectionList = new LinkedList<>();

    private ServerSocket init() {
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

    private Socket initClientConnection(ServerSocket serverSocket) {
        Socket socket;

        try {
            socket = serverSocket.accept();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return socket;
    }

    public void sendMessageToClients(String message) {
        for(Connection c : connectionList) {
            try {
                PrintWriter out = c.getOutputStream();
                out.println(message);
                out.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void listConnections() {
        for(Thread c : connectionThreadList) {
            System.out.println("thread name: " + c.getName());

        }

        for(Connection c : connectionList) {
            System.out.println("client name: " + c.getClientName());
        }
    }

    public void quit() {
        for(Connection c : connectionList) {
            c.close();
        }

        connectionThreadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("error closing threads: " + e);
            }
        });
    }

    private void closeUnusedThreads() {
        while (true) {
            connectionList.forEach(conn -> {
                if(conn.getSocket().isClosed()) {

                    connectionThreadList.forEach(thread -> {
                        try {
                            if (thread.getName().equals(conn.getClientName())) {
                                thread.join();
                            }
                        } catch (InterruptedException e) {
                            System.out.println("error closing threads: " + e);
                        }
                    });

                }
            });
        }
    }

    @Override
    public void run() {
        ServerSocket server = this.init();

        while (true) {
            Socket clientSocket = this.initClientConnection(server); // blocking method
            Connection conn = new Connection(this, clientSocket);
            Thread thread = new Thread(conn, clientSocket.getPort()+ "");

            thread.start();
            connectionThreadList.add(thread);
            connectionList.add(conn);
        }
    }
}
