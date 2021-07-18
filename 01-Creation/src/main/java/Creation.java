public class Creation {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Started thread. Current thread:" + Thread.currentThread().getName());
            }
        });

        System.out.println("Before starting new thread. Current thread:" + Thread.currentThread().getName());
        thread.start();
        System.out.println("Before starting new thread. Current thread:" + Thread.currentThread().getName());

        // This instructs the OS to not schedule the thread until the specified time passes
        Thread.sleep(5000);
    }
}
