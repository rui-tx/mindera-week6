package primefinder;

import java.util.List;

public class Prime implements Runnable {

    final List<Integer> numbers;

    public Prime(List<Integer> numbers) {
        this.numbers = numbers;
    }

    //https://byjus.com/maths/factors-of-a-number/
    private boolean isNumberPrime(int number) {
        int factorTotal = 0;
        for (int i = 1; i <= number; i++) {
            if (factorTotal > 3) {
                return false;
            }

            if (number % i == 0) {
                factorTotal++;
            }
        }

        return factorTotal == 2;
    }

    @Override
    public void run() {
        synchronized (numbers) {
            for (int i = 0; i < numbers.size(); i++) {
                if (!isNumberPrime(i)) {
                    numbers.set(i, 0);
                }
            }
        }
    }
}
