package quoteserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

// Server is static class
// client is not
// client sends commands and get response from server

public class Server {

    private static String name;
    private static int port;

    public static void init() {

        return;
    }

    public static void main(String[] args) throws IOException {
        int portNumber = 15000;
        String hostName = "localhost";

        DatagramSocket socket = new DatagramSocket(portNumber);
        System.out.println("[server]> starting server...");
        while (!socket.isClosed()) {
            byte[] recvBuffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(
                    recvBuffer, recvBuffer.length);
            System.out.println("[server]> waiting for clients...");
            socket.receive(receivedPacket); // blocking method!
            System.out.println("[server]> receive command from client on port: " + receivedPacket.getPort());

            //checkPacket()

            String receivedCommand = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            if (!receivedCommand.equals("hit me")) {

                String data = "unsupported operation: " + receivedCommand;

                DatagramPacket sendPacket = new DatagramPacket(
                        data.getBytes(), data.getBytes().length,
                        receivedPacket.getAddress(), receivedPacket.getPort());

                socket.send(sendPacket);
                System.out.println("[server]> response sent to client: " + data);

                continue;
            }

            //byte[] sendBuffer = new byte[1024];
            String data = new String(getRandomQuote().getBytes());
            DatagramPacket sendPacket = new DatagramPacket(
                    data.getBytes(), data.getBytes().length,
                    receivedPacket.getAddress(), receivedPacket.getPort());

            socket.send(sendPacket);
            System.out.println("[server]> response sent to client: " + data);

        }

        socket.close();
    }

    public static String getRandomQuote() {
        Random rand = new Random();
        return Quotes.values()[rand.nextInt(Quotes.values().length)].getQuote();

    }
}