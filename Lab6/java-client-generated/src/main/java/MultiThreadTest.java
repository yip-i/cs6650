import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadTest implements Runnable {
    SkiersApi apiInstance = new SkiersApi();
    String basePath = "http://localhost:8080/servlet_war_exploded";
    ApiClient client = apiInstance.getApiClient();
    Integer resortID = 56; // Integer | ID of the resort
    String seasonID = "56"; // String | ID of the season
    String dayID = "56"; // String | ID of the day
    Integer skierID = 56; // Integer | ID of the skier

    public MultiThreadTest(){
        client.setBasePath(basePath);
    }

    @Override
    public void run() {
        try {
            ApiResponse res = apiInstance.getSkierDayVerticalWithHttpInfo(resortID, seasonID, dayID, skierID);
            System.out.println(res.getStatusCode());
            Integer verticalResult = apiInstance.getSkierDayVertical(resortID, seasonID, dayID, skierID);
            System.out.println(verticalResult);
        } catch (ApiException e) {
            System.err.println("Exception when calling SkiersApi#getSkierDayVertical");
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(300);
        Instant start = Instant.now();

        for(int i = 0; i < 100; i++) {
            es.execute(new MultiThreadTest());

        }
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Elapsed time: " + duration.getSeconds() + "." + duration.getNano());

    }

}
