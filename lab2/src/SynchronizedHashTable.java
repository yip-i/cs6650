import java.time.Duration;
import java.time.Instant;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedHashTable implements Runnable {
    private static Hashtable<Integer,Integer> table = new Hashtable<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    private static int numElements = 100;
    private static int perThreadNumElements = 1;
    private static final Object lock = new Object();

    public SynchronizedHashTable(){

    }



    public void start(int numElements) {
        this.numElements = numElements;
        this.perThreadNumElements = this.numElements / 100;
        Instant start = Instant.now();

        for(int i = 0; i < 100; i++) {
            Thread thread = new Thread(new SynchronizedHashTable());
            thread.start();
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

                table.put(counter.getAndIncrement(), 0);
            }
        }
    }


    public int getSize() {
        return table.size();

    }

    public void print() {
        for(Integer i: table.keySet()) {
            String key = i.toString();
            String value = table.get(i).toString();
            System.out.println("Key: " + key + " Value: " + value);
        }
    }

    public void printCounter() {
        System.out.println(counter.get());
    }
}
