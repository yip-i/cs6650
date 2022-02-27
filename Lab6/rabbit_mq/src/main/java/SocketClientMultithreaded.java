/**
 * Skeleton socket client. 
 * Accepts host/port on command line or defaults to localhost/12031
 * Then starts MAX_Threads and waits for them all to terminate before terminating main()
 * @author Ian Gorton
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientMultithreaded {

    static CyclicBarrier barrier;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException  {
        String hostName;
        final int MAX_THREADS = 30 ;
        int port;

        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName= null;
            port = 12031;  // default port in SocketServer
        }
        //initialization of barrier for the threads
        barrier = new CyclicBarrier(MAX_THREADS+1);
        Instant start = Instant.now();
        //create and start MAX_THREADS SocketClientThread
        for (int i=0; i< MAX_THREADS; i++){
            new SocketClientThread(hostName, port, barrier).start();
        }

        //wait for all threads to complete
        barrier.await();
        Instant stop = Instant.now();

        System.out.println("Terminating ....");
        Duration duration = Duration.between(start, stop);
        System.out.println("Number of threads: " + MAX_THREADS);
        System.out.println(duration.getSeconds() + "." + duration.getNano());

    }
}
