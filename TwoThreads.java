package przyklady01;

public class TwoThreads {

    private static void printName() {
        System.out.println(Thread.currentThread());
    }

    private static class Helper implements Runnable {

        @Override
        public void run() {
            printName();
        }

    }

    public static void main(String[] args) {
        Thread.currentThread().setName("Główny");
        Runnable r = new Helper();
        Thread t = new Thread(r, "Pomocniczy");
        t.start();
        printName();
    }

}
