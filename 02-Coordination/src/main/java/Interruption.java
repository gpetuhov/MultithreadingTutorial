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
                // Thread.sleep() responds to the interrupt signal and throws InterruptedException,
                // so we don't have to check for interrupt signal ourselves.
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                // We just have to catch InterruptedException and handle interruption here
                System.out.println("Exiting blocking thread");
            }
        }
    }
}
