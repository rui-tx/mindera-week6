package chat.server;

import java.io.*;
import java.net.Socket;

public class Connection implements Runnable {

    private Socket socket;
    private String clientName;
    private Server server;

    public Connection(Server server,  Socket socket) {
        this.server = server;
        this.socket = socket;

    }

    public Socket getSocket() {
        return socket;
    }

    public String getClientName() {
        return clientName;
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientName = socket.getPort() + "";
            System.out.println("[server]< connection accept from client ");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (socket.isBound()) {
            in.lines().forEach(e -> { //blocking method, waits until client sends something
                PrintWriter out;
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    //System.out.println("[server]< accepted " + e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //System.out.println("[server]> send success ");
                //out.println("success");
                server.sendMessageToClients("[" + socket.getPort() + "]>" + e);
                out.flush();
            });
        }

        this.close();
    }

    public PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(this.socket.getOutputStream(), true);
    }
}
