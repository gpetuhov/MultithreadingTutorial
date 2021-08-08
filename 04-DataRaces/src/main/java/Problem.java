public class Problem {

    public static void main(String[] args) throws InterruptedException {

        SharedClass sharedClass = new SharedClass();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    private static class SharedClass {
        private int x = 0;
        private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                // This situation, although seems impossible,
                // will occur multiple times
                // due to out-of-order execution,
                // which is the result of
                // the compiler and CPU optimizations.
                // This is called Data Race condition.
                // The solution is to access shared variables inside synchronized block,
                // which may result in performanse decrease,
                // or to use volatile keyword.
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}