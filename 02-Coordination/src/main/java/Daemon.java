public class Daemon {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new TimerTask());

        // Daemon thread does NOT prevent the application from exiting
        // if the main thread terminates.
        thread.setDaemon(true);

        thread.start();

        Thread.sleep(1000);

        // The application will exit here even though the background thread is still running.
        // Background thread will also be terminated.
    }

    private static class TimerTask implements Runnable {

        @Override
        public void run() {
            // This task runs an infinite loop and doesn't check for the interrupt signal
            while (true) {
                System.out.println(System.currentTimeMillis());
            }
        }
    }
}
