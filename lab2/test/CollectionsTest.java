import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollectionsTest {

    @Test
    public void collectionsTest1(){
        Collections1 collections = new Collections1();

        int numElements = 100_000;
        collections.addVector(numElements);
        collections.addList(numElements);
        collections.addHashMap(numElements);
        collections.addHashTable(numElements);
        collections.addConcurrentHashMap(numElements);

        assertEquals(numElements, collections.getArraySize());
        assertEquals(numElements, collections.getHashMapSize());
    }

    @Test
    public void SynchronizedHashMapTest() {
        SynchronizedHashMap sync = new SynchronizedHashMap();
        sync.start(1000);
        //sync.print();
        assertEquals(1000, sync.getSize());
    }


    @Test
    public void SynchronizedHashMapTest1() {
        SynchronizedHashMap sync = new SynchronizedHashMap();
        for(int i = 0; i < 2; i++) {
            sync.start(100000);
        }
        //sync.print();
        assertEquals(200000, sync.getSize());
    }



}
