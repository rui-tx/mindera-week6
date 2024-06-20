package todoudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

    private static String hostName = "localhost";
    private static int portNumber = 15000;
    private static DatagramSocket socket;
    private static TodoList list = new TodoList("shared todos");

    private static void init() throws SocketException {
        socket = new DatagramSocket(portNumber);
    }

    private static DatagramPacket receiveData() throws IOException {

        byte[] recvBuffer = new byte[1024];
        DatagramPacket receivedPacket = new DatagramPacket(
                recvBuffer, recvBuffer.length);
        socket.receive(receivedPacket);
        return receivedPacket;
    }

    private static DatagramPacket runCommand(DatagramPacket receivedPacket) throws IOException {

        String decodedCommand = new String(receivedPacket.getData(), 0, receivedPacket.getLength());

        String data = new String(decodedCommand.getBytes());
        String cmd = data.substring(0, 1);

        switch (cmd) {
            case "h":
                data = "c [todo] - create new todo; l - list todos";
                break;
            case "c":
                list.addTask(data.substring(2));
                data = "new todo added: " + data.substring(2);
                break;
            case "r":
                Integer id = Integer.parseInt(data.substring(2, 3));
                try {
                    list.removeTask(id);
                    data = "Todo item " + id + " removed";
                } catch (Exception e) {
                    data = "No todo item with id " + id + " found";
                }
                
                break;
            case "l":
                data = list.getAllTasksString();
                break;

            default:
                data = "unsupported operation\n use: c [todo] - create new todo; l - list todos";
        }

        return createPacket(receivedPacket, data);
    }

    private static DatagramPacket createPacket(DatagramPacket receivedPacket, String data) {

        return new DatagramPacket(
                data.getBytes(), data.getBytes().length,
                receivedPacket.getAddress(), receivedPacket.getPort());
    }

    public static void main(String[] args) {

        try {
            init();
        } catch (SocketException e) {
            System.out.println("[server]> Error making server: " + e.getMessage());
            return;
        }

        System.out.printf("[server]> starting server on port %s\n", portNumber);
        while (!socket.isClosed()) {
            System.out.println("[server]> waiting for clients...");
            DatagramPacket receivedPacket; // blocking method!
            try {
                receivedPacket = receiveData();
            } catch (IOException e) {
                System.out.println("[server]> Error receiving data from client: " + e.getMessage());
                continue;
            }

            System.out.printf("[server]> receive command from client %s:%d\n",
                    receivedPacket.getAddress(), receivedPacket.getPort());


            DatagramPacket sendPacket;
            try {
                sendPacket = runCommand(receivedPacket);
                socket.send(sendPacket);
            } catch (IOException e) {
                System.out.println("[server]> Error sending data to client: " + e.getMessage());
                continue;
            }

            System.out.println("[server]> response sent to client");
        }

        socket.close();
    }
}


//String receivedCommand = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
//sendPacket = runCommand(receivedPacket);
            /*
            if (!receivedCommand.equals("hit me")) {

                String data = "unsupported operation: " + receivedCommand;

                DatagramPacket sendPacket = new DatagramPacket(
                        data.getBytes(), data.getBytes().length,
                        receivedPacket.getAddress(), receivedPacket.getPort());

                socket.send(sendPacket);
                System.out.println("[server]> response sent to client: " + data);

                continue;
            }

             */