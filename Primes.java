package przyklady01;

import java.util.Arrays;

public class Primes {
    public static int first_number = 2;
    public static int last_number = 10000;
    private static volatile boolean found = false;

    public static int[] first_check = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
    public static int[] starters = {31, 37, 41, 43, 47, 49, 53, 59};

    public static boolean[] thread_ended = new boolean[starters.length];

    public static boolean all_threads_ended() {
        for (boolean ended : thread_ended) {
            if (!ended) {
                return false;
            }
        }
        return true;
    }

    private static class Helper implements Runnable {
        private final int id;
        private final int starter;
        private final int possible_prime;

        public Helper(int id, int starter, int n) {
            this.id = id;
            this.starter = starter;
            this.possible_prime = n;
        }

        @Override
        public void run() {
            int i = starter;
            while (i <= possible_prime && !found) {
                if (possible_prime % i == 0 && possible_prime != i) {
                    found = true;
                }
                i += 30;
            }
            thread_ended[id] = true;
        }
    }

    static boolean isPrime(int n) {
        for (int possible_devider : first_check) {
            if (n % possible_devider == 0 && n != possible_devider) {
                return false;
            }
        }

        for (int i = 0; i < starters.length; i++) {
            Thread t = new Thread(new Helper(i, starters[i], n));
            t.start();
        }

        while (!all_threads_ended()) {

        }
        if (found) {
            found = false;
            Arrays.fill(thread_ended, false);
            return false;
        }

        Arrays.fill(thread_ended, false);
        return true;
    }

    static int count_primes() {
        int counter = 0;
        for (int i = first_number; i <= last_number; i++) {
            if (isPrime(i)) {
                counter++;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println(count_primes());
    }

}
