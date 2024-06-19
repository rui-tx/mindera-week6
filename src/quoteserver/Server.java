package quoteserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class Server {

    public static void main(String[] args) throws IOException {
        int portNumber = 8088;
        String hostName = "localhost";

        DatagramSocket socket = new DatagramSocket(portNumber);
        while (true) {
            byte[] recvBuffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(
                    recvBuffer, recvBuffer.length);
            System.out.println("waiting...");
            socket.receive(receivedPacket); // blocking method!
            System.out.println("receive from client on port: " + receivedPacket.getPort());

            byte[] sendBuffer = new byte[1024];
            String data = new String(getRandomQuote().getBytes());

            DatagramPacket sendPacket = new DatagramPacket(
                    data.getBytes(), data.getBytes().length,
                    receivedPacket.getAddress(), receivedPacket.getPort());

            socket.send(sendPacket);
            System.out.println("send to client quote");

        }

        // CLOSE THE SOCKET
        //socket.close();
    }

    public static String getRandomQuote() {
        Random rand = new Random();
        return Quotes.values()[rand.nextInt(Quotes.values().length)].getQuote();

    }
}