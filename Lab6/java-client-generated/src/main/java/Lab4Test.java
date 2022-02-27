import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

public class Lab4Test {


    public static void main(String[] args) throws FileNotFoundException {

        // Example for the GET
        SkiersApi apiInstance = new SkiersApi();
        String basePath = "http://localhost:8080/servlet_war_exploded";

        ApiClient client = apiInstance.getApiClient();
        client.setBasePath(basePath);
        Integer resortID = 56; // Integer | ID of the resort
        String seasonID = "56"; // String | ID of the season
        String dayID = "56"; // String | ID of the day
        Integer skierID = 56; // Integer | ID of the skier
        LiftRide liftRide = new LiftRide();

        Instant start = Instant.now();
        Instant end = Instant.now();
        Duration duration;
        Vector<Integer> responses = new Vector<>();
        Vector<String> times = new Vector<>();
        String time;
        for(int i = 0; i < 100; i++) {
            try {
                start = Instant.now();

                liftRide.setWaitTime(100);
                liftRide.setLiftID(120);
                liftRide.setTime(30);


                ApiResponse res = apiInstance.writeNewLiftRideWithHttpInfo(liftRide, 35, "2020", "monday", 29);
//                System.out.println(res.getStatusCode());
//            Integer verticalResult = apiInstance.getSkierDayVertical(resortID, seasonID, dayID, skierID);
//            System.out.println(verticalResult);

                end = Instant.now();
                duration = Duration.between(start, end);
                time = (duration.getSeconds() + "." + duration.getNano());
                responses.add(res.getStatusCode());
                times.add(time);

            } catch (ApiException e) {
                System.err.println("Exception when calling SkiersApi#getSkierDayVertical");
                e.printStackTrace();
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < responses.size(); i++){
            sb.append(responses.get(i));
            sb.append(",");
            sb.append(times.get(i));
            sb.append("\n");
        }
        PrintWriter pw = new PrintWriter("lab4.csv");
        pw.write(sb.toString());
        pw.flush();
        pw.close();
        System.out.println("Finished");

    }
}
