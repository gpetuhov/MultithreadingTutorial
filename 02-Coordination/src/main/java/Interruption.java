public class Interruption {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new BlockingTask());

        thread.start();

        // The application will run as long as at least one thread is running,
        // even if the main thread is already finished.

        // Interrupt allows one thread to try to interrupt execution of another thread
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                // InterruptedException is thrown if interrupt() method of a thread is called
                System.out.println("Exiting blocking thread");
            }
        }
    }
}
