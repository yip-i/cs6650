import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMap1 implements Runnable{
    private static ConcurrentHashMap<Integer,Integer> map = new ConcurrentHashMap<>();

    private static AtomicInteger counter = new AtomicInteger(0);

    private static int numElements = 100;
    private static int perThreadNumElements = 1;
    private static final Object lock = new Object();

    public ConcurrentHashMap1(){

    }


    public void start(int numElements) {
        this.numElements = numElements;
        this.perThreadNumElements = this.numElements / 100;
        Instant start = Instant.now();
        ArrayList<Thread> arrThread = new ArrayList<>();

        ExecutorService es = Executors.newCachedThreadPool();
        for(int i = 0; i < 100; i++) {
            es.execute(new ConcurrentHashMap1());
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

    public synchronized void add() {
        synchronized (lock) {
            for(int i = 0; i < this.perThreadNumElements; i++) {

                map.put(counter.getAndIncrement(), 0);
            }
        }
    }


    public int getSize() {
        return map.size();
    }

    public void printCounter() {
        System.out.println(counter.get());
    }
}
