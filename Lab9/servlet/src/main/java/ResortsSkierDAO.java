import java.sql.*;
import org.apache.commons.dbcp2.*;


public class ResortsSkierDAO {

    private static BasicDataSource dataSource;

    public ResortsSkierDAO() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public int getResortRides(int resortID, int seasonID, int dayID) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        String selectQueryStatement = "SELECT COUNT(distinct( skierID)) AS skierCount FROM LiftRides " +
                "WHERE resortID = ? AND seasonID = ? AND dayID = ?;";


        String output = "";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(selectQueryStatement);
            preparedStatement.setInt(1, resortID);
            preparedStatement.setInt(2, seasonID);
            preparedStatement.setInt(3, dayID);
//            System.out.println(preparedStatement.toString());

            // execute insert SQL statement

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int skierCount = rs.getInt("skierCount");
            return skierCount;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return 0;
    }
}
