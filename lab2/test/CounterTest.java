
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CounterTest {

    @Test public void firstTest() {

        int threadCounter = 1000000;
        Counter counter = new Counter(threadCounter);


        counter.start();


        assertEquals(threadCounter * 10, counter.getCounter());

    }
}
