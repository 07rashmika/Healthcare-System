package DBController;

import java.sql.*;

public class DBConnection {

    //Database Connection Credentials
    private String url = "jdbc:sqlserver://sliitdb.cbewo8icgg6v.us-east-1.rds.amazonaws.com:1433;"
            + "databaseName=SLIIT;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    private String username = "admin";
    private String password = "admin123";
    public Connection con;//Connection object


    //Connection Method to start the connection
    public void startConnection(){

        try{
        con = DriverManager.getConnection(url,username,password);
            System.out.println("Database Connection Success");

        }
        catch (SQLException e){
            System.out.println("Couldn't Connect to database");
        }
    }


}
