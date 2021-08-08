public class Synchronization1 {

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

        // Result will always be correct = 0
        System.out.println(counter.getCounter());
    }

    private static class Counter {
        private int counter = 0;

        // For synchronized methods the lock is the object itself
        // this is equivalent to:
        //        public void increment() {
        //            synchronized (this) {
        //                counter++;
        //            }
        //        }
        // Therefore if thread A enters synchronized method 1,
        // no other thread can enter neither synchronized method 1
        // nor any other synchronized methods of this object.
        public synchronized void increment() {
            counter++;
        }

        public synchronized void decrement() {
            counter--;
        }

        public synchronized int getCounter() {
            return counter;
        }
    }
}