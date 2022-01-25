import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedHashMap implements Runnable{
    private static HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    private static Map<Integer,Integer> synchronizedMap = Collections.synchronizedMap(hashMap);
    private static AtomicInteger counter = new AtomicInteger(0);

    private static int numElements = 100;
    private static int perThreadNumElements = 1;

    public SynchronizedHashMap(){

    }



    public void start(int numElements) {
        this.numElements = numElements;
        this.perThreadNumElements = this.numElements / 100;
        Instant start = Instant.now();

        for(int i = 0; i < 100; i++) {
            Thread thread = new Thread(new SynchronizedHashMap());
            thread.start();
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }


    public synchronized void run() {
        synchronized (synchronizedMap) {
            for (int i = 0; i < this.perThreadNumElements; i++) {
                Integer x = counter.getAndIncrement();
                synchronizedMap.put(x, x);
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
