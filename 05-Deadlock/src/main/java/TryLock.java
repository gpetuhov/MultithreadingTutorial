import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

// When the thread MUST NOT BE BLOCKED by waiting for the lock to be released
// by another thread (for example, UI thread),
// use ReentrantLock.tryLock() instead of synchronized.
public class TryLock {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread thread1 = new Thread(new Operation1(counter));
        Thread thread2 = new Thread(new Operation2(counter));

        thread1.start();
        thread2.start();
    }


    public static class Counter {
        private final ReentrantLock lock1 = new ReentrantLock();
        private final ReentrantLock lock2 = new ReentrantLock();

        private int counter1 = 0;
        private int counter2 = 0;

        public void increment1() {
            if (lock1.tryLock()) {
                System.out.println("Lock 1 is locked by thread " + Thread.currentThread().getName());

                try {
                    if (lock2.tryLock()) {
                        System.out.println("Running operation 1");
                        try {
                            counter1++;
                            counter2++;
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } finally {
                            lock2.unlock();
                        }
                    } else {
                        System.out.println("Lock 2 is already locked by another thread. Doing other work");
                        // Do something else
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } finally {
                    // Always unlock ReentrantLock in finally block
                    // (so that the lock would be unlocked in case main operation throws exception).
                    lock1.unlock();
                }

            } else {
                System.out.println("Lock 1 is already locked by another thread. Doing other work");
                // Do something else
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void increment2() {
            if (lock1.tryLock()) {
                System.out.println("Lock 1 is locked by thread " + Thread.currentThread().getName());

                try {
                    if (lock2.tryLock()) {
                        System.out.println("Running operation 2");
                        try {
                            counter1++;
                            counter2++;
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } finally {
                            lock2.unlock();
                        }
                    } else {
                        System.out.println("Lock 2 is already locked by another thread. Doing other work");
                        // Do something else
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } finally {
                    // Always unlock ReentrantLock in finally block
                    // (so that the lock would be unlocked in case main operation throws exception).
                    lock1.unlock();
                }

            } else {
                System.out.println("Lock 1 is already locked by another thread. Doing other work");
                // Do something else
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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