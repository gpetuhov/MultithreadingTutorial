public class ThreadException {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Something went wrong");
            }
        });

        thread.setName("New Worker Thread");

        // Instead of using try-catch block we can set exception handler for the thread
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Exception in thread: " + t.getName() + ", error: " + e.getMessage());
            }
        });

        thread.start();
    }
}
