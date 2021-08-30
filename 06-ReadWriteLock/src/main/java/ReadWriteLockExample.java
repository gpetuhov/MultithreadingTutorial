import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    public static void main(String[] args) throws InterruptedException {
        MyStorage storage = new MyStorage();
        Random random = new Random();

        Thread writer = new Thread(() -> {
            while (true) {
                storage.addItem(random.nextInt(Integer.MAX_VALUE));
                try {
                    Thread.sleep(0, 10);
                } catch (InterruptedException e) {
                }
            }
        });

        writer.setDaemon(true);
        writer.start();
        
        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();

        for (int i = 0; i < numberOfReaderThreads; i++) {
            Thread reader = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    storage.readSum();
                }
            });

            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();

        for (Thread reader : readers) {
            reader.start();
        }

        for (Thread reader : readers) {
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();

        System.out.println("Measurement result is: " + (endReadingTime - startReadingTime));
    }

    public static class MyStorage {
        private List<Integer> list = new ArrayList<>();

        private ReentrantLock reentrantLock = new ReentrantLock();

        // ReentrantReadWriteLock allows several threads acquire
        // read lock simultaneously
        // (because if value is not changed, then it can safely be read by several threads),
        // but only ONE thread at a time to acquire write lock.
        // If write lock is acquired, read lock can NOT be acquired.
        // Notice that ReentrantReadWriteLock is better than ReentrantLock
        // only with READ intensive workloads!
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = reentrantReadWriteLock.readLock();
        private Lock writeLock = reentrantReadWriteLock.writeLock();

        public void addItem(Integer number) {
//            reentrantLock.lock();
            writeLock.lock();
            try {
                list.add(number);
            } finally {
//                reentrantLock.unlock();
                writeLock.unlock();
            }
        }

        public Long readSum() {
//            reentrantLock.lock();
            readLock.lock();
            try {
                // Mimic long read operation
                Thread.sleep(0, 1);

                if (list.isEmpty()) {
                    return 0L;
                } else {
                    long sum = 0;
                    for (Integer number : list) {
                        sum += number;
                    }
                    return sum;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 0L;
            } finally {
//                reentrantLock.unlock();
                readLock.unlock();
            }
        }
    }
}
