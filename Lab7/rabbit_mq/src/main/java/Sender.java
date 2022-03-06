import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Sender {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare("hello-world", false, false, false, null);
            String message = "is this the matrix?";
            channel.basicPublish("", "hello-world", false, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Message has been sent");
        }
    }

}
