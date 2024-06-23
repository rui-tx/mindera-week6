package restaurant;

import java.util.ArrayList;

public class Client implements Runnable {

    ArrayList<String> dishs;
    private int dishsConsumed;
    private int MAX_CONSUMED = 2;

    public Client(ArrayList<String> dishs) {

        this.dishs = dishs;

    }

    // not used
    @Override
    public void run() {

        while (dishsConsumed < MAX_CONSUMED) {
            synchronized (dishs) {
                for (int i = 0; i < dishs.size(); i++) {
                    if (!dishs.get(i).isEmpty()) {
                        dishs.set(i, "");
                        System.out.println("client consumed a dish");
                        dishsConsumed++;
                        break;
                        //return;
                    }
                }
            }
            try {
                System.out.println("client is sleeping");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("client left");


    }
}
