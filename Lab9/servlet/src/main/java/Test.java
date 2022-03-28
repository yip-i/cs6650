public class Test {

    public static void main(String[] args) {
        LiftRideDao liftRideDao = new LiftRideDao();
        System.out.println(System.getProperty("MYSQL_IP_ADDRESS"));
        System.out.println(System.getProperty("user.dir"));
        liftRideDao.createLiftRide(new LiftRide(10, 2, 3, 5, 500, 20));
    }
}