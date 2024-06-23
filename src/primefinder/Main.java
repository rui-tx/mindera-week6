package primefinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("prime finder starting...");
        long startTime = System.currentTimeMillis();

        int max = 1000000;
        int numberOfThreads = 32;
        List<Thread> threadList = new LinkedList<>();

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            numbers.add(i);
        }

        // divide the list into the number of threads
        List<List<Integer>> parts = divide(numbers, numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            Thread prime = new Thread(new Prime(parts.get(i)));

            prime.start();
            threadList.add(prime);
        }

        // wait for completion
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("error closing threads: " + e);
            }
        });

        // join parts in numbers
        numbers.clear();
        for (List<Integer> list : parts) {
            numbers.addAll(list);
        }

        /*
        for (Integer n : numbers.stream().filter(e -> !e.equals(0)).toList()) {
            System.out.print(n + " ");
        }
        System.out.println();
         */

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("list size: " + max);
        System.out.println("primes found: " + numbers.stream().filter(e -> !e.equals(0)).count());
        System.out.println("number os threads: " + numberOfThreads);
        System.out.println("runtime of " + elapsedTime + " ms");
    }

    public static <T> List<List<T>> divide(List<T> list, int numberOfParts) {
        List<List<T>> parts = new ArrayList<>();
        int totalSize = list.size();
        int partSize = totalSize / numberOfParts;
        int remainder = totalSize % numberOfParts;

        int start = 0;
        for (int i = 0; i < numberOfParts; i++) {
            int end = start + partSize + (remainder > 0 ? 1 : 0);
            parts.add(new ArrayList<>(list.subList(start, Math.min(end, totalSize))));
            start = end;
            if (remainder > 0) {
                remainder--;
            }
        }

        return parts;
    }
}
