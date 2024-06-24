package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        while (true) {
            System.out.print("[command]> ");
            try {
                command = reader.readLine();

                if (command.equals("l")) {
                    server.listConnections();
                }

                if (command.equals("t")) {
                    server.sendMessageToClients("[server]> this is a test message sent from the server");
                }

                if (command.equals("c")) {
                    server.closeUnusedThreads();
                }

                if (command.equals("q")) {
                    System.exit(1);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
