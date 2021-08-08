public class Problem {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Result will always be different
        // (this is called Race Condition)
        System.out.println(counter.getCounter());
    }

    private static class Counter {
        private int counter = 0;

        public void increment() {
            counter++;
        }

        public void decrement() {
            counter--;
        }

        public int getCounter() {
            return counter;
        }
    }
}