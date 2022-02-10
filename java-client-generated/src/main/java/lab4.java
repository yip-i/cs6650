import com.google.gson.Gson;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class lab4 implements Runnable {

    static AtomicInteger startup = new AtomicInteger(0);
    static AtomicInteger numRequests = new AtomicInteger(0);
    static Vector<Integer> statusCodes = new Vector<>();

    int startID;
    int endID;
    int numThreads;
    int numLifts;
    int numRuns;
    int startTime;
    int endTime;
    SkiersApi apiInstance = new SkiersApi();
    ApiClient client = apiInstance.getApiClient();



    public lab4(int startID, int endID, int numThreads, int numLifts, int numRuns, int startTime, int endTime, String basepath) {
        this.startID = startID;
        this.endID = endID;
        this.numThreads = numThreads;
        this.numLifts = numLifts;
        this.numRuns = numRuns;
        this.startTime = startTime;
        this.endTime = endTime;

        client.setBasePath(basepath);
    }

    public void resetAtomicInteger() {
        startup.set(0);
    }

    public Vector getStatusCodes() {
        return statusCodes;
    }

    @Override
    public void run() {
        int i = 0;
        int skierID;
        int lift;
        int waitTime;
        int time;
        Random random = new Random();
        ApiResponse res;


        LiftRide liftRide = new LiftRide();
        Gson gson = new Gson();
        String request;

        while(i < numRuns * (this.endID - this.startID) && startup.get() < numThreads * 0.2) {
            i++;

            skierID = random.nextInt(endID - startID) + startID;
            lift = random.nextInt(numLifts);
            waitTime = random.nextInt(10);
            time = random.nextInt(this.endTime - this.startTime) + this.startTime;

            liftRide.setLiftID(lift);
            liftRide.setWaitTime(waitTime);
            liftRide.setTime(time);


            try {
                res = apiInstance.writeNewLiftRideWithHttpInfo(liftRide, 100, "123", "24", skierID);
//                System.out.println(res.getStatusCode());
                statusCodes.add(res.getStatusCode());
//                numRequests.incrementAndGet();
            } catch (ApiException e) {
                e.printStackTrace();
            }

            //            res = apiInstance.getSkierDayVertical();

        }

//        System.out.println("I= " + i);
        startup.incrementAndGet();
        return;
    }


    /**
     * Input the parameters with the name followed by a space.
     * @param args
     */
    public static void main(String[] args) {
        int numThreads = - 1;
        int numSkiers = -1;
        int numLifts = 40;
        int numRuns = 10;
        String ipAddress = "localhost";
        String basePath = "http://";
        for(int i = 0; i < args.length; i+=2) {
            if(args[i].equalsIgnoreCase("numThreads")) {
                numThreads = Integer.parseInt(args[i + 1]);
            } else if( args[i].equalsIgnoreCase("numSkiers")) {
                numSkiers = Integer.parseInt(args[i + 1]);
            } else if( args[i].equalsIgnoreCase("numLifts")) {
                numLifts = Integer.parseInt(args[i + 1]);
            } else if( args[i].equalsIgnoreCase("numRuns")) {
                numRuns = Integer.parseInt(args[i + 1]);
            } else if (args[i].equalsIgnoreCase("IPAddress")) {
                ipAddress = args[i + 1];
            }
        }

        // Error checking
        if(numThreads < 1 || numThreads > 1024) {
            System.out.println("Thread input error");
            return;
        } else if (numSkiers < 1 || numSkiers > 100000) {
            System.out.println("Skier input error");
            return;
        } else if (numLifts < 5 || numLifts > 60) {
            System.out.println("Lift input error");
            return;
        } else if (numRuns < 1 || numRuns > 20) {
            System.out.println("Run input error");
            return;
        }
        basePath = basePath + ipAddress + ":8080/servlet_war";
        System.out.println(basePath);

        lab4 stats = new lab4(0,0,0,0,0,0,0, "");

        ExecutorService es = Executors.newFixedThreadPool(numThreads);
        Instant start = Instant.now();

        //Start up
        int startupNumThreads = numThreads/ 4;
        int skierInterval = numSkiers / startupNumThreads;
        for(int i = 0; i < startupNumThreads; i++) {

            es.execute(new lab4(skierInterval * i, (skierInterval * i) + skierInterval - 1, startupNumThreads, numLifts, (int) (numRuns * 0.2), 0, 90, basePath));

        }
//        System.out.println("Skier interval: "+ skierInterval + " startup num threads: " + startupNumThreads );

        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Reset atomic integer
        stats.resetAtomicInteger();

        //Peak
        es = Executors.newFixedThreadPool(numThreads);
        int peakSkierInterval = numSkiers / numThreads;

        for(int i = 0; i < numThreads; i++) {

            es.execute(new lab4(peakSkierInterval * i, (peakSkierInterval * i) + peakSkierInterval - 1, numThreads, numLifts, (int) (numRuns * 0.6), 361, 420, basePath));

        }
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        //End
        stats.resetAtomicInteger();

        es = Executors.newFixedThreadPool(numThreads);

        int endNumThreads = numThreads / 10;
        if (endNumThreads < 1) {
            endNumThreads = 1;
        }
        int endSkierInterval = numSkiers / endNumThreads;
        for(int i = 0; i < endNumThreads; i++) {

            es.execute(new lab4(endSkierInterval * i, (endSkierInterval * i) + endSkierInterval, endNumThreads, numLifts, (int) (numRuns * 0.1), 361, 420, basePath));

        }
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        System.out.println(stats.getStatusCodes().size());
        Vector v = stats.getStatusCodes();
        int numberOf201 = 0;
        for(int i = 0; i < v.size(); i++ ) {
            if(v.get(i).equals(201)) {
                numberOf201 ++;
            }
        }
        System.out.println("Correct response codes: " + numberOf201);
        System.out.println("Incorrect response codes: " + (v.size() - numberOf201));
        System.out.println("duration:" + duration);
        System.out.println(duration.getSeconds());
        System.out.println(duration.getNano());
    }


}
