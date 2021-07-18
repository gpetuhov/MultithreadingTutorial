import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Join {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(100000000L, 234L, 4354L, 23L, 9874L, 3422L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            // This is needed so that long calculations will not prevent our application from exiting
            thread.setDaemon(true);

            thread.start();
        }

        // Without this code block we would see the output "is still in progress"
        // for most of the input numbers, because the calculations will not be finished yet.
        for (Thread thread : threads) {
            // Join method will return only when this thread is terminated
//            thread.join();

            // It is better to wait for a specific amount of time to prevent our application
            // from "hanging" in case of very long calculations.
            thread.join(2000);
        }

        // We will get here only when all the threads are terminated

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    // Here we have some long calculations (factorial in this example)
    private static class FactorialThread extends Thread {

        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }
    }
}
