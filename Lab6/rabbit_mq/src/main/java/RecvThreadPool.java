import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecvThreadPool {

    public static void main(String [] args) {
        int numThreads = 1;
        String ip = "34.214.184.109";
//        String[] ips = {"52.42.103.74", "18.237.225.145", "54.188.247.124", "34.209.255.188"};
        String username = "test";
        String password = "test";
        ExecutorService es = Executors.newFixedThreadPool(numThreads);
//        for(int i = 0; i < numThreads; i++) {
//            es.execute(new RecvThread(ip, username, password));
//        }
        for(int i = 0; i < numThreads; i++) {
//            ip = ips[i % ips.length];
            es.execute(new RecvThread(ip, username, password));
        }
    }



}
