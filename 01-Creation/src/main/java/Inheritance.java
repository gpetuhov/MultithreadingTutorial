public class Inheritance {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MyThread();

        thread.start();
    }

    // We can create our thread by extending Thread and overriding its run method
    // (Thread itself implements the Runnable interface)
    private static class MyThread extends Thread {

        @Override
        public void run() {
            // Inside the thread we can access thread methods by calling "this"
            System.out.println("Current thread:" + this.getName());
        }
    }
}
