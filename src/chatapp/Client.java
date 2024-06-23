package chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static Socket init(String host, int port) {
        Socket socket;

        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return socket;
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 15000;
        Socket clientSocket = init(host, port); // blocking method
        if (clientSocket == null) {
            return;
        }

        BufferedReader in;
        PrintWriter out;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String command;
        //Scanner reader = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (clientSocket.isBound()) {
            System.out.print("[command]> ");
            //command = reader.next();
            try {
                command = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (command.equals("exit")) {
                break;
            }

            out.println(command);
            out.flush();

            try {
                System.out.printf("[response]> %s\n", in.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
