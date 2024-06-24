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
        for (Connection c : connectionList) {
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
        for (Thread c : connectionThreadList) {
            System.out.println("thread name: " + c.getName());
        }

        for (Connection c : connectionList) {
            System.out.println("client name: " + c.getClientName());
        }
    }

    public void quit() {
        connectionThreadList.forEach(thread -> {
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                System.out.println("error closing threads: " + e);
            }
        });

        for (Connection c : connectionList) {
            try {
                c.getSocket().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void closeUnusedThreads() {

        // a connection has a thread associated with, so if a connection is closed, we close the thread too
        connectionList.forEach(conn -> {
            if (conn.isCloseMe()) {
                try {
                    this.sendMessageToClients("[server]> [" + conn.getSocket().getPort() + "] disconnected the server");
                    conn.getSocket().close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                connectionThreadList.forEach(thread -> {
                    try {
                        if (thread.getName().equals(conn.getClientName())) {
                            thread.join(250);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("error closing threads: " + e);
                    }
                });
            }
        });

        // remove closed connections and dead threads
        connectionList.removeIf(e -> e.getSocket().isClosed());
        connectionThreadList.removeIf(t -> !t.isAlive());
    }

    @Override
    public void run() {
        ServerSocket server = this.init();

        // check for unused connections
        Thread checkConnections = new Thread(() -> {
            try {
                while (true) {
                    this.closeUnusedThreads();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        checkConnections.start();

        while (true) {
            Socket clientSocket;
            synchronized (server) {
                clientSocket = this.initClientConnection(server); // blocking method
            }

            Connection conn = new Connection(this, clientSocket);
            Thread thread = new Thread(conn, clientSocket.getPort() + "");

            thread.start();
            connectionThreadList.add(thread);
            connectionList.add(conn);
            this.sendMessageToClients("[server]> [" + clientSocket.getPort() + "] joined the server");
        }
    }
}
