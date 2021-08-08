public class Synchronization2 {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment1();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement1();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment2();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement2();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Both results will always be correct = 0
        System.out.println(counter.getCounter1());
        System.out.println(counter.getCounter2());
    }

    private static class Counter {
        private int counter1 = 0;
        private int counter2 = 0;

        // For different critical sections we can have different locks.
        // Here for counter1 we have lock1,
        // and lock2 - for counter2.
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        public void increment1() {
            synchronized (lock1) {
                counter1++;
            }
        }

        public void decrement1() {
            synchronized (lock1) {
                counter1--;
            }
        }

        public int getCounter1() {
            return counter1;
        }

        public void increment2() {
            synchronized (lock2) {
                counter2++;
            }
        }

        public void decrement2() {
            synchronized (lock2) {
                counter2--;
            }
        }

        public int getCounter2() {
            return counter2;
        }
    }
}