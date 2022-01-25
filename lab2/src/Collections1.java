import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Collections1 {
    private Vector<Integer> vector = new Vector<>();
    private ArrayList<Integer> lst = new ArrayList<>();
    private Hashtable<Integer, Integer> hashTable = new Hashtable<Integer, Integer>();
    private HashMap<Integer, Integer> hashMap = new HashMap<>();
    private ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    public Collections1(){

    }

    public void addVector(int numElements) {

        Instant start = Instant.now();


        for (int i = 0; i < numElements; i++) {
            vector.add(i);
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time vector: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }

    public void addList(int numElements) {

        Instant start = Instant.now();


        for (int i = 0; i < numElements; i++) {
            lst.add(i);
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time List: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }

    public void addHashTable(int numElements) {

        Instant start = Instant.now();


        for (int i = 0; i < numElements; i++) {
            hashTable.put(i,i);
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time HashTable: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }

    public void addHashMap(int numElements) {

        Instant start = Instant.now();


        for (int i = 0; i < numElements; i++) {
            hashMap.put(i,i);
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time HashMap: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }

    public void addConcurrentHashMap(int numElements) {

        Instant start = Instant.now();


        for (int i = 0; i < numElements; i++) {
            concurrentHashMap.put(i,i);
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        System.out.println("Elapsed time ConcurrentHashMap: " + timeElapsed.getSeconds() + "." + timeElapsed.getNano());
    }


    int getArraySize() {
        return lst.size();
    }

    int getHashMapSize() {
        return hashMap.size();
    }




}
