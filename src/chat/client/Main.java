package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 15000;
        Client client = new Client();
        Thread clientThread = null;


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        while (true) {
            System.out.print("[command]> ");
            try {
                command = reader.readLine();

                if (command.equals("c")) {
                    Socket clientSocket = client.init(host, port); // blocking method
                    if (clientSocket == null) {
                        continue;
                    }

                    if (!clientSocket.isConnected()) {
                        System.out.println("client already connected");
                        continue;
                    }

                    clientThread = new Thread(client);
                    clientThread.start();

                    break;
                    /*
                    try {
                        clientThread.join();
                        System.out.println("test");

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                     */
                }

                if (command.equals("q")) {
                    try {
                        if (clientThread != null) {
                            clientThread.join();
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.exit(1);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
