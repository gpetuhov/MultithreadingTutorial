import java.math.BigInteger;

public class Interruption2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("100000000000")));

        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {

        private final BigInteger base;
        private final BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {

                // In computation tasks we have to constantly check if the current thread is interrupted
                // and manually stop computations in this case.
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted computation");
                    return BigInteger.ZERO;
                }

                result = result.multiply(base);
            }

            return result;
        }
    }
}
