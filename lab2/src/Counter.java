import java.util.concurrent.atomic.AtomicInteger;
import java.time.Duration;
import java.time.Instant;

/**
 * Class representing a counter in Java.
 * https://tharakamd-12.medium.com/4-ways-to-implement-a-synchronized-counter-in-java-5540452fb4f7
 *
 */
public class Counter implements Runnable {
    private static AtomicInteger counter = new AtomicInteger(0);
    private int numThreads;


    public Counter() {
        numThreads = 1000;
    }

    /**
     * Constructor for the
     * @param threadCounter Number of threads
     */
    public Counter(int threadCounter) {
        this.numThreads = threadCounter;
    }

    public void start() {
        Instant start = Instant.now();

        for(int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new Counter());
            thread.start();
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }

    /**
     *
     */
    @Override
    public void run() {
        for (int i = 0; i < 10; i++){
            counter.getAndIncrement();
        }

    }

    /**
     * Gets the value of the counter.
     * @return Value of the counter.
     */
    public int getCounter(){
        return this.counter.get();
    }
}
