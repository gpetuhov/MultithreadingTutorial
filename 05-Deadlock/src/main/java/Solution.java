import java.util.Random;

public class Solution {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread thread1 = new Thread(new Operation1(counter));
        Thread thread2 = new Thread(new Operation2(counter));

        thread1.start();
        thread2.start();
    }


    public static class Counter {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        private int counter1 = 0;
        private int counter2 = 0;

        public void increment1() {
            synchronized (lock1) {
                System.out.println("Lock 1 is locked by thread " + Thread.currentThread().getName());

                synchronized (lock2) {
                    System.out.println("Running operation 1");
                    counter1++;
                    counter2++;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void increment2() {
            // The simplest solution is to acquire locks IN THE SAME ORDER
            synchronized (lock1) {
                System.out.println("Lock 1 is locked by thread " + Thread.currentThread().getName());

                synchronized (lock2) {
                    System.out.println("Running operation 2");
                    counter2++;
                    counter1++;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class Operation1 implements Runnable {

        private Counter counter;
        private Random random = new Random();

        public Operation1(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                counter.increment1();
            }
        }
    }

    public static class Operation2 implements Runnable {

        private Counter counter;
        private Random random = new Random();

        public Operation2(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                counter.increment2();
            }
        }
    }
}