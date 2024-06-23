package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 15000;
        Client client = new Client();

        Socket clientSocket = client.init(host, port); // blocking method
        if (clientSocket == null) {
            return;
        }

        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
