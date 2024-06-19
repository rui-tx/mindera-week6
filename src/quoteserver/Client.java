package quoteserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {
        int serverPortNumber = 8088;
        String hostName = "localhost";

        DatagramSocket socket = new DatagramSocket();

        byte[] sendBuffer = new byte[1024];
        DatagramPacket sendPacket = new DatagramPacket(
                sendBuffer, sendBuffer.length,
                InetAddress.getByName(hostName), serverPortNumber);
        socket.send(sendPacket);
        System.out.println("sending packet to server " + hostName + ":" + serverPortNumber);

        byte[] recvBuffer = new byte[1024];
        DatagramPacket receivedPacket = new DatagramPacket(
                recvBuffer, recvBuffer.length);
        System.out.println("waiting for response...");
        socket.receive(receivedPacket); // blocking method!
        //String data = new String(receivedPacket.getData(), 0, receivedPacket.getData().length);
        System.out.println("receive from server: " + new String(receivedPacket.getData(), 0, receivedPacket.getLength()));
    }
}