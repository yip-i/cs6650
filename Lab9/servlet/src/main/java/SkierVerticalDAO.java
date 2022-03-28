import java.sql.*;
import org.apache.commons.dbcp2.*;


public class SkierVerticalDAO {
    private static BasicDataSource dataSource;

    public SkierVerticalDAO() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public int getSkierVertical(SkierVertical skierVertical) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(skierVertical.SQLRequest());

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int skierVerticalCount = rs.getInt("verticalCount");
            return skierVerticalCount;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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

