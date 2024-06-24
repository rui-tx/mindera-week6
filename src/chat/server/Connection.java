package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {

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
                    if (!message.isEmpty()) {
                        if (message.charAt(0) == '#') {
                            String cmd = message.substring(1, 2);
                            switch (cmd) {

                                case "h":
                                    out.println("help");
                                    this.dontBroadcast = true;
                                    break;

                                case "r":
                                    if (message.substring(2).length() > 1) {
                                        this.currentRoom = message.substring(2);
                                        out.println("Joined: " + this.currentRoom);
                                        out.println("[" + socket.getPort() + "]>");
                                        this.dontBroadcast = true;
                                        break;
                                    }
                                    break;

                                case "l":
                                    out.println("Room: " + this.currentRoom + " (" + this.server.getConnectionList().stream()
                                            .filter(e -> e.currentRoom.equals(this.currentRoom))
                                            .count()
                                            + ")");

                                    //not working for some reason
                                    /*
                                    this.server.getConnectionList().stream()
                                            .filter(e -> e.currentRoom.equals(this.currentRoom))
                                            .forEach(e -> out.println(e.getClientName()));

                                    this.server.getConnectionList().forEach(c -> {
                                        if (this.currentRoom.equals(c.currentRoom)) {
                                            out.println("Client Name: " + c.getClientName());
                                        }
                                    });
                                     */
                                    this.dontBroadcast = true;
                                    break;

                                case "e":
                                    if (!this.currentRoom.equals("Global")) {
                                        out.println("Room left: " + this.currentRoom);
                                        this.currentRoom = "Global";
                                        this.dontBroadcast = true;
                                    }
                                    break;

                                case "x":
                                    this.close();
                                    this.dontBroadcast = true;
                                    return;

                                default:
                                    break;
                            }
                        }
                    }

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