package restaurant;

import java.util.ArrayList;

public class Cook implements Runnable {

    ArrayList<String> dishs;

    public Cook(ArrayList<String> dishs) {

        this.dishs = dishs;

        //dishs.stream().filter(e -> e.isEmpty()).forEach(e -> makeDish());
        /*
        for (int i = 0; i < dishs.size(); i++) {
            if (dishs.get(i).isEmpty()) {
                dishs.set(i, "dish");
                return;
            }
        }
        //dishs.add(makeDish());
         */
    }

    public String makeDish() {
        return "dish";
    }

    @Override
    public void run() {

        while (true) {
            synchronized (dishs) {
                for (int i = 0; i < dishs.size(); i++) {
                    if (dishs.get(i).isEmpty()) {
                        dishs.set(i, "dish");
                        System.out.println("cook made a new dish");
                        break;
                        //return;
                    }
                }
            }
            try {
                System.out.println("cook is sleeping");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
