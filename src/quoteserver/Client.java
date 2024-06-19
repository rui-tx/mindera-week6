package quoteserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {
        int serverPortNumber = 15000;
        String hostName = "localhost";

        DatagramSocket socket = new DatagramSocket();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = "";
        while (true) {
            System.out.print("[command]> ");
            command = reader.readLine();

            if (command.equals("exit")) {
                break;
            }

            DatagramPacket sendPacket = new DatagramPacket(
                    command.getBytes(), command.getBytes().length,
                    InetAddress.getByName(hostName), serverPortNumber);
            socket.send(sendPacket);
            System.out.println("[client]> sending packet to server " + hostName + ":" + serverPortNumber);

            byte[] recvBuffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(
                    recvBuffer, recvBuffer.length);
            System.out.println("[client]> waiting for response...");
            socket.receive(receivedPacket); // blocking method!
            System.out.println("[server]> " + new String(receivedPacket.getData(), 0, receivedPacket.getLength()));
        }
    }
}