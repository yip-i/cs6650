import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedHashMap implements Runnable {
    private static Map<Integer,Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
    private static AtomicInteger counter = new AtomicInteger(0);

    private static int numElements = 100;
    private static int perThreadNumElements = 1;
    private static final Object lock = new Object();

    public SynchronizedHashMap(){

    }



    public void start(int numElements) {
        this.numElements = numElements;
        this.perThreadNumElements = this.numElements / 100;
        Instant start = Instant.now();
        ExecutorService es = Executors.newCachedThreadPool();

        for(int i = 0; i < 100; i++) {
            es.execute(new SynchronizedHashMap());

        }

        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }

    @Override
    public void run() {
        add();

    }

    public void add() {
        synchronized (lock) {
            for (int i = 0; i < this.perThreadNumElements; i++) {
                synchronizedMap.put(counter.getAndIncrement(), 0);
            }
        }
    }

    public int getSize() {
        return synchronizedMap.size();

    }

    public void print() {
        for(Integer i: synchronizedMap.keySet()) {
            String key = i.toString();
            String value = synchronizedMap.get(i).toString();
            System.out.println("Key: " + key + " Value: " + value);
        }
    }
}
