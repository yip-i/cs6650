import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleThreadTest {
    SkiersApi apiInstance = new SkiersApi();
    String basePath = "http://54.244.63.104:8080/servlet_war";
    ApiClient client = apiInstance.getApiClient();
    Integer resortID = 56; // Integer | ID of the resort
    String seasonID = "56"; // String | ID of the season
    String dayID = "56"; // String | ID of the day
    Integer skierID = 56; // Integer | ID of the skier

    public SingleThreadTest(){
        client.setBasePath(basePath);
    }

    public void run() {
        Instant start = Instant.now();
        Instant end = Instant.now();
        Duration duration;
        for(int i = 0; i < 10000; i++) {
            start = Instant.now();
            try {
                ApiResponse res = apiInstance.getSkierDayVerticalWithHttpInfo(resortID, seasonID, dayID, skierID);
//                System.out.println(res.getStatusCode());

            } catch (ApiException e) {
                System.err.println("Exception when calling SkiersApi#getSkierDayVertical");
                e.printStackTrace();
            }
            end = Instant.now();
            duration = Duration.between(start, end);
            System.out.println(duration.getSeconds() + "." + duration.getNano() + ",");


        }

    }

    public static void main(String[] args) throws InterruptedException {
        SingleThreadTest single = new SingleThreadTest();
        single.run();



    }
}
