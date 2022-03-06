import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

public class RecvThread implements Runnable{
    static ConcurrentHashMap<Integer,Vector<String>> map = new ConcurrentHashMap<>();

    ConnectionFactory factory = new ConnectionFactory();
    Connection connection;
    Channel channel;
    static Vector<String> recvList = new Vector<>();
    private final static String QUEUE_NAME = "hello";
    private LiftRideDao liftRideDao = new LiftRideDao();

    RecvThread(String ipAddress, String username, String password){
        factory.setHost(ipAddress);
        factory.setUsername(username);
        factory.setPassword(password);
        try {
            this.connection = this.factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        try {
            this.channel = this.connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        Gson gson = new Gson();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received '" + message + "'");
            recvList.add(message);
//            System.out.println(message);
            LiftRide lift = (LiftRide) gson.fromJson(message, LiftRide.class);

            map.putIfAbsent(lift.getSkierID(), new Vector<String>());
            map.get(lift.getSkierID()).add(lift.toString());
            liftRideDao.createLiftRide(lift);
            System.out.println("Messages received: " + recvList.size());
        };
        try {
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    synchronized private void insertMap() {

    }
}
