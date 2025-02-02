import DBController.DBConnection;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        Connection conn = DBConnection.startConnection();
        if (conn != null) {
            System.out.println("Database is connected!");
        } else {
            System.out.println("Failed to connect to database.");
        }
    }
}
