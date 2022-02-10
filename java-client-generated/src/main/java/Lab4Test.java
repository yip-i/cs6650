import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

public class Lab4Test {


    public static void main(String[] args) {

        // Example for the GET
        SkiersApi apiInstance = new SkiersApi();
        String basePath = "http://localhost:8080/servlet_war_exploded";

        ApiClient client = apiInstance.getApiClient();
        client.setBasePath(basePath);
        Integer resortID = 56; // Integer | ID of the resort
        String seasonID = "56"; // String | ID of the season
        String dayID = "56"; // String | ID of the day
        Integer skierID = 56; // Integer | ID of the skier
        try {
            LiftRide liftRide = new LiftRide();
            liftRide.setWaitTime(100);
            liftRide.setLiftID(120);
            liftRide.setTime(30);


            ApiResponse res = apiInstance.writeNewLiftRideWithHttpInfo(liftRide, 35, "2020","monday", 29);
            System.out.println(res.getStatusCode());
//            Integer verticalResult = apiInstance.getSkierDayVertical(resortID, seasonID, dayID, skierID);
//            System.out.println(verticalResult);


        } catch (ApiException e) {
            System.err.println("Exception when calling SkiersApi#getSkierDayVertical");
            e.printStackTrace();
        }

    }
}
