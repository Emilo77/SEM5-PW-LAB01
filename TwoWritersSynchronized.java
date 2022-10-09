package przyklady01;

public class TwoWritersSynchronized {

    private static volatile boolean firstDone = false;
    private static volatile boolean secondDone = false;

    private static volatile int currentId;

    private static class Writer implements Runnable {

        private static final int LINES_COUNT = 100;
        private static final int LINE_LENGTH = 50;

        private final char first;
        private final char last;
        private final int id;

        public Writer(char first, char last, int id) {
            this.first = first;
            this.last = last;
            this.id = id;
        }

        @Override
        public void run() {
            char c = first;
            for (int i = 0; i < LINES_COUNT; ++i) {
                while (currentId != id) {
                    // empty
                }
                for (int j = 0; j < LINE_LENGTH; ++j) {
                    System.out.print(c);
                    ++c;
                    if (c > last) {
                        c = first;
                    }
                }
                System.out.println();
                currentId = 1 - currentId;
            }
            if (id == 0) {
                firstDone = true;
            } else {
                secondDone = true;
            }
        }

    }

    public static void main(String args[]) {
        Thread first = new Thread(new Writer('a', 'z', 0));
        Thread second = new Thread(new Writer('0', '9', 1));
        currentId = 0;
        System.out.println("Początek");
        first.start();
        second.start();
        while (!firstDone) {
            // pusta
        }
        while (!secondDone) {
            // pusta
        }
        System.out.println("Koniec");
    }

}
