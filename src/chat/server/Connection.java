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


    public Connection(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getClientName() {
        return clientName;
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
                    if (message.equals("exit")) {
                        this.close();
                        return;
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                synchronized (server) {
                    server.sendMessageToClients("[" + socket.getPort() + "]>" + message); //broadcast message to all connected clients
                }
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