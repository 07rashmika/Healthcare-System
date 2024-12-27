package DBController;

import java.sql.*;

public class DBConnection {

    //Database Connection Credentials
    private static String url = "jdbc:sqlserver://sliitdb.cbewo8icgg6v.us-east-1.rds.amazonaws.com:1433;"
            + "databaseName=OOP;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    private static String username = "admin";
    private static String password = "admin123";
    private static Connection con;//Connection object


    //Connection Method to start the connection
    public static Connection startConnection(){

        try{
        con = DriverManager.getConnection(url,username,password);
            System.out.println("Database Connection Success");

        }
        catch (SQLException e){
            System.out.println("Couldn't Connect to database");
        }
        return con;
    }


}
