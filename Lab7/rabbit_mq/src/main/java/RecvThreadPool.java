import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecvThreadPool {

    public static void main(String [] args) {
        int numThreads = 40;
        String ip = "35.87.147.173";
        String[] ips = {"35.166.200.255", "54.214.219.66", "35.162.81.118", "35.87.73.211"};
        String username = "test";
        String password = "test";
        ExecutorService es = Executors.newFixedThreadPool(numThreads);
//        for(int i = 0; i < numThreads; i++) {
//            es.execute(new RecvThread(ip, username, password));
//        }
        for(int i = 0; i < numThreads; i++) {
            ip = ips[i % ips.length];
            es.execute(new RecvThread(ip, username, password));
        }
    }



}
