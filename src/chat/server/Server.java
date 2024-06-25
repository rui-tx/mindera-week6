package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final String PRETTY_NAME = "terminal chatapp";
    private final String HOST = "localhost";
    private final int PORT = 15000;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final List<Connection> connectionList = new LinkedList<>();
    private final Map<String, Connection> roomList = new HashMap<>();


    public List<Connection> getConnectionList() {
        return connectionList;
    }

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
            if (c.getCurrentRoom().equals("Global")) {
                try {
                    PrintWriter out = c.getOutputStream();
                    out.println(message);
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessageToClients(String roomName, String message) {
        for (Connection c : connectionList) {
            if (c.getCurrentRoom().equals(roomName)) {
                try {
                    PrintWriter out = c.getOutputStream();
                    out.println(message);
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void listConnections() {
        for (Connection c : connectionList) {
            System.out.println("client name: " + c.getClientName() + " on room " + c.getCurrentRoom());
        }
    }

    public void quit() {
        for (Connection c : connectionList) {
            try {
                c.getSocket().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void closeUnusedConnections() {

        // a connection has a thread associated with, so if a connection is closed, we close the thread too
        connectionList.forEach(conn -> {
            if (conn.isCloseMe()) {
                try {
                    this.sendMessageToClients("[server]> [" + conn.getSocket().getPort() + "] disconnected");
                    System.out.println("[server]> [" + conn.getSocket().getPort() + "] disconnected");
                    conn.getSocket().close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // remove closed connections
        connectionList.removeIf(e -> e.getSocket().isClosed());
    }

    public void newRoom(String roomName) {
        //this.roomList.add(roomName);
    }

    public void addClientToRoom(Connection connection, String roomName) {
        this.roomList.put(roomName, connection);
    }

    @Override
    public void run() {
        ServerSocket server = this.init();

        // check for unused connections
        Thread checkConnections = new Thread(() -> {
            try {
                while (true) {
                    this.closeUnusedConnections();
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

            threadPool.submit(thread);
            connectionList.add(conn);
            this.sendMessageToClients("[server]> [" + clientSocket.getPort() + "] joined the server");
        }
    }

    public static class Connection implements Runnable {

        private final Server server;
        private Socket socket;
        private String clientName;
        private BufferedReader in;
        private PrintWriter out;
        private boolean closeMe;
        private String currentRoom;
        private boolean dontBroadcast;

        public Connection(Server server, Socket socket) {
            this.server = server;
            this.socket = socket;

            this.currentRoom = "Global";
        }


        public Socket getSocket() {
            return socket;
        }

        public String getClientName() {
            return clientName;
        }

        public String getCurrentRoom() {
            return currentRoom;
        }

        public boolean isCloseMe() {
            return closeMe;
        }

        public void close() {
            this.closeMe = true;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // input stream from the current connection
                this.clientName = socket.getPort() + "";
                System.out.println("[server]< connection accept from client " + this.clientName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            while (!socket.isClosed()) {
                //in.lines().forEach(e -> {});
                try {
                    String message = in.readLine(); //blocking method, waits until client sends something
                    if (message == null) {
                        this.close();
                        return;
                    }

                    try {
                        out = new PrintWriter(socket.getOutputStream(), true); // output stream from the current connection

                        //if (!message.isEmpty()) {
                        if (message.startsWith("/")) {

                            String cmd;
                            String parameter = "";
                            
                            String[] splitCmd = message.split(" ", 2);
                            if (splitCmd.length == 1) {
                                cmd = splitCmd[0];
                            } else {
                                cmd = message.split(" ", 2)[0];
                                parameter = message.split(" ", 2)[1];
                            }

                            switch (cmd) {
                                case "/help":
                                    out.println("help");
                                    this.dontBroadcast = true;
                                    break;

                                case "/room":
                                    if (parameter.length() > 1) {
                                        this.currentRoom = parameter;
                                        out.println("Joined: " + this.currentRoom);
                                        out.println("[" + socket.getPort() + "]>");
                                        this.dontBroadcast = true;
                                        break;
                                    }
                                    break;

                                case "/list":
                                    out.println("Room: " + this.currentRoom + " (" + this.server.getConnectionList().stream()
                                            .filter(e -> e.currentRoom.equals(this.currentRoom))
                                            .count()
                                            + ")");
                                    this.dontBroadcast = true;
                                    break;

                                case "/leave":
                                    if (!this.currentRoom.equals("Global")) {
                                        out.println("Room left: " + this.currentRoom);
                                        this.currentRoom = "Global";
                                        this.dontBroadcast = true;
                                    }
                                    break;

                                case "/exit":
                                    this.close();
                                    this.dontBroadcast = true;
                                    return;

                                default:
                                    out.println("Invalid command.");
                                    this.dontBroadcast = true;
                                    break;
                            }
                        }
                        //}

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    if (!this.dontBroadcast) {
                        synchronized (server) {
                            if (this.currentRoom.equals("Global")) {
                                server.sendMessageToClients("[" + socket.getPort() + "]>" + message); //broadcast message to all connected clients
                            } else {
                                server.sendMessageToClients(this.currentRoom, "[" + socket.getPort() + "]>" + message); //broadcast message to all connected clients
                            }
                        }
                    }
                    this.dontBroadcast = false;
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            this.close();
        }

        public PrintWriter getOutputStream() throws IOException {
            return new PrintWriter(this.socket.getOutputStream(), true);
        }
    }
}
