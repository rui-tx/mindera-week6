package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private Socket socket;

    public Socket init(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            System.out.println("Your ID is : [" + this.socket.getLocalPort() + "]");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return socket;
    }

    private void close() throws IOException {
        this.socket.close();
    }

    @Override
    public void run() {
        // read thread
        Thread read = new Thread(() -> {
            while (this.socket.isBound()) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    System.out.printf("\n%s\n", in.readLine());
                } catch (IOException e) {
                    return;
                    //throw new RuntimeException(e);
                }
            }

            try {
                this.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        // write thread
        Thread write = new Thread(() -> {
            PrintWriter out;
            try {
                out = new PrintWriter(this.socket.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String command;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (this.socket.isBound()) {
                //System.out.print("[" + this.socket.getLocalPort() + "]> ");
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
            }

            try {
                this.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        read.start();
        write.start();
    }
}
