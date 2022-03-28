
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.dbcp2.BasicDataSource;


@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
    private static SkierVerticalDAO skierVerticalDAO;

    public void init() {
        skierVerticalDAO = new SkierVerticalDAO();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        String urlPath = req.getPathInfo();


        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing paramterers");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)


        if (urlParts.length == 3) {
            Gson gson = new Gson();
            try {
                StringBuilder sb = new StringBuilder();
                String s;
                while(((s = req.getReader().readLine()) != null)) {
                    sb.append(s);
                    sb.append("\n");
                }
                SkierVertical skierVertical = (SkierVertical) gson.fromJson(sb.toString(), SkierVertical.class);

                skierVertical.setSkierID(Integer.parseInt(urlParts[1]));
                System.out.println(skierVertical.SQLRequest());

                VerticalOutput verticalOutput = new VerticalOutput();
                verticalOutput.setTotalVert(skierVerticalDAO.getSkierVertical(skierVertical));
                verticalOutput.setSeasonID("" + skierVertical.getSeason());
//                System.out.println(sb.toString());
                String output = gson.toJson(verticalOutput);
//                System.out.println(output);
                PrintWriter out = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                out.print(output);
                out.flush();
                res.setContentType("text/plain");

                res.setStatus(HttpServletResponse.SC_CREATED);

            } catch (Exception ex) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            }
        }
        else if (!isUrlValid(urlParts)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`
            Gson gson = new Gson();

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            SkierVertical skierVertical = new SkierVertical("" + Integer.parseInt(urlParts[1]), "" + Integer.parseInt(urlParts[3]));
            skierVertical.setDayID(Integer.parseInt(urlParts[5]));
            skierVertical.setSkierID(Integer.parseInt(urlParts[7]));
            System.out.println(skierVertical.SQLRequest());
            String rides = gson.toJson(skierVerticalDAO.getSkierVertical(skierVertical));
            PrintWriter out = res.getWriter();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            out.print(rides);
            out.flush();

            res.setContentType("text/plain");
//            res.getWriter().write("It works!");
        }
    }

    /**
     * Checks if the url is valid.
     * @param urlPath
     * @return
     */
    private boolean isUrlValid(String[] urlPath) {
        // TODO: validate the request url path according to the API spec
        // urlPath  = "/1/seasons/2019/days/1/skiers/123"
        // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
//        System.out.println(urlPath[1]);
        if(!urlPath[2].equalsIgnoreCase("seasons")) {
            return false;
        } else if(!urlPath[4].equalsIgnoreCase("days")){
            return false;
        } else if(!urlPath[6].equalsIgnoreCase("skiers")){
            return false;
        }


        if(!isInt(urlPath[1]) || !isInt(urlPath[7])){
            return false;
        }

        return true;
    }

    /**
     * Checks if a string is an integer or not.
     * @param s String value that we are checking.
     * @return True if it s is an int, false otherwise.
     */
    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing paramterers");
            return;
        }

        String[] urlParts = urlPath.split("/");

        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)

        if (!isUrlValid(urlParts)) {

            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`

            Gson gson = new Gson();
            try {
                StringBuilder sb = new StringBuilder();
                String s;
                while(((s = req.getReader().readLine()) != null)) {
                    sb.append(s);
                    sb.append("\n");
                }


                LiftRide liftRide = (LiftRide) gson.fromJson(sb.toString(), LiftRide.class);
                try{
                    liftRide.setSkierID(Integer.parseInt(urlParts[7]));
                    liftRide.setResortID(Integer.parseInt(urlParts[1]));
                    liftRide.setSeasonID(Integer.parseInt(urlParts[3]));
                    liftRide.setDayID(Integer.parseInt(urlParts[5]));
                } catch (NumberFormatException e) {
                    System.out.println("Exception");
                }

//                System.out.println(lift.toString());
                if (liftRide.isValidLift()){

                    res.setStatus(HttpServletResponse.SC_CREATED);
                    Send(liftRide.sendJson());

                    Send1(liftRide.sendJson());
//                    LiftRideDao liftRideDao = new LiftRideDao();
//                    liftRideDao.createLiftRide(liftRide);
                } else {
                    System.out.println(liftRide.toString());
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                }
            } catch (Exception ex) {
//                res.getWriter().write("Error message");
                System.out.println("False");

                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            }


//            res.getWriter().write("Ian's servlet works!");
        }
    }

    private void Send(String message) {
        String QUEUE_NAME = "hello";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("54.190.42.207");
        factory.setUsername("test");
        factory.setPassword("test");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private void Send1(String message) {
        String QUEUE_NAME = "queue2";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("54.190.42.207");
        factory.setUsername("test");
        factory.setPassword("test");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    // create inputStreamFromClasspath() method to load the JSON data from the class path
    private static InputStream inputStreamFromClasspath(String path ) {

        // returning stream
        return Thread.currentThread().getContextClassLoader().getResourceAsStream( path );
    }
//
//    private boolean isSkierJSONValid(String input) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance( SpecVersion.VersionFlag.V201909 );
//        try {
////            InputStream schemaStream = inputStreamFromClasspath("src/main/resources/skierSchema.json");
//            // read data from the stream and store it into JsonNode
//
//            System.out.println(input);
//
//            JsonNode node = objectMapper.readTree(input);
//            System.out.println(node.textValue());
//
//            // get schema from the schemaStream and store it into JsonSchema
//            JsonSchema schema = schemaFactory.getSchema("{\n    \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n    \"title\": \"Product\",\n    \"description\": \"A product from the catalog\",\n    \"type\": \"object\",\n    \"properties\": {\n    \"time\": {\n        \"description\": \"Time of the ride\",\n            \"type\": \"integer\",\n                \"minimum\": 0\n    },\n    \"liftID\": {\n        \"description\": \"ID of the lift taken\",\n            \"type\": \"integer\",\n                \"minimum\": 0\n    },\n    \"waitTime\": {\n        \"type\": \"integer\",\n            \"minimum\": 0,\n            \"exclusiveMinimum\": true\n    }\n},\n    \"required\": [\"time\", \"liftID\", \"waitTime\"]\n}");
//            System.out.println(schema.toString());
//            // create set of validation message and store result in it
//            Set<ValidationMessage> validationResult = schema.validate( node );
//
//            // show the validation errors
//            if (validationResult.isEmpty()) {
//                return true;
//                // show custom message if there is no validation error
////                System.out.println( "There is no validation errors" );
//
//            } else {
//                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
//
//                return false;
//                // show all the validation error
//            }
//
//        } catch (JsonMappingException e) {
//
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//
//        } catch (JsonProcessingException e) {
//            System.out.println(e.getMessage());
//
//            e.printStackTrace();
//
//        }
//
//        return false;
//    }
}
