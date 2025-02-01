package DBController;

import java.sql.*;

public class ReportDBConnection {
    private static String url = "jdbc:sqlserver://sliitdb.cbewo8icgg6v.us-east-1.rds.amazonaws.com:1433;"
            + "databaseName=OOP;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    private static String username = "admin";
    private static String password = "admin123";
    private static Connection con;

    // Method to start a connection to the database
    public static Connection startConnection(){
        try {
            // Manually load the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Attempt to connect to the database
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connection Success");
        } catch (SQLException e) {
            System.out.println("Couldn't Connect to database");
            e.printStackTrace();  // This will print the detailed stack trace for easier troubleshooting
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found");
            e.printStackTrace();  // If the driver class is not found, this will catch it
        }
        return con;
    }

    // Method to close the connection safely
    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Database Connection Closed");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection");
            e.printStackTrace();
        }
    }
}
