import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollectionsTest {

    @Test
    public void collectionsTest1(){
        Collections1 collections = new Collections1();

        int numElements = 100;
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
        sync.start(1000_000);
        //sync.print();
        assertEquals(1000_000, sync.getSize());
    }




    @Test
    public void SynchronizedHashTableTest() {

        SynchronizedHashTable table = new SynchronizedHashTable();
        table.start(1000_000);
        //sync.print();
        table.printCounter();
        assertEquals(1000_000, table.getSize());
    }

    @Test
    public void ConcurrentHashMapTest() {

        ConcurrentHashMap1 map = new ConcurrentHashMap1();
        map.start(10000_000);
        //sync.print();
        map.printCounter();

        assertEquals(10000_000, map.getSize());

    }


}
