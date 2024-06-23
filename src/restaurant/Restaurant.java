package restaurant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Restaurant {

    private final int ARRAY_LENGTH = 5;
    private volatile ArrayList<String> dishs;
    private List<Thread> clientList;
    private List<Thread> cookList;
    private int numberOfCooks = 5;

    public Restaurant() {
        dishs = new ArrayList<>();
        this.cookList = new LinkedList<>();
        this.clientList = new LinkedList<>();


        for (int i = 0; i < ARRAY_LENGTH; i++) {
            dishs.add("");
        }
    }

    public void open() {
        /*
        while (!this.checkIfDishsAreFull()) {
            Thread cook = new Thread(() -> {
                synchronized (dishs) {
                    new Cook(dishs);
                }
            });
            cook.start();
            cookList.add(cook);
        }

         */

        for (int i = 0; i < numberOfCooks; i++) {
            Thread cook = new Thread(new Cook(this.dishs));

            cook.start();
            cookList.add(cook);
        }
        
    }

    public void addClients(int numberOfClients) {
        for (int i = 0; i < numberOfClients; i++) {
            Thread client = new Thread(new Client(dishs));

            client.start();
            clientList.add(client);
        }
    }

    public void addDishs() {
        while (!this.checkIfDishsAreFull()) {
            Thread cook = new Thread(() -> {
                synchronized (dishs) {
                    new Cook(dishs);
                }
            });
            cook.start();
            cookList.add(cook);
        }
    }

    public void addDishs(int numberOfDishs) {
        while (numberOfDishs > 0) {
            Thread cook = new Thread(() -> {
                synchronized (dishs) {
                    new Cook(dishs);
                }
            });
            cook.start();
            cookList.add(cook);
            numberOfDishs--;
        }
    }

    public void close() {
        if (cookList != null) {
            cookList.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.out.println("error closing cook threads: " + e);
                }
            });
        }

        if (clientList != null) {
            clientList.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.out.println("error closing client threads: " + e);
                }
            });
        }

        printDishs();
    }

    public void printDishs() {
        //dishs.forEach(System.out::println);
        for (int i = 0; i < dishs.size(); i++) {
            System.out.println(i + " " + dishs.get(i));
        }
    }

    private boolean checkIfDishsAreFull() {
        return dishs.stream().noneMatch(String::isEmpty);
    }

}
