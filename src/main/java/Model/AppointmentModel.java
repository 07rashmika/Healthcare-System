package Model;


import DBController.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentModel {

    private final Connection con = DBConnection.startConnection();

    //Adding the doctor names into a array list to retrieve and add it into the doctorOption combo box
    public ArrayList<String> getDoctors(){

        ArrayList<String> doctors = new ArrayList<>();
        String query = "SELECT DoctorID,FirstName FROM SA24610154_Doctors";


            try{
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);//executes the query and places the cursor before the first row

                //moves the cursor to the next row, runs as longs as there is a row
                while(rs.next()){
                    doctors.add(rs.getString("DoctorID") + " - " + rs.getString("FirstName"));
                }
            }
            catch (Exception e){
                System.out.println("Couldn't retrieve doctor data " + e.getMessage());
            }

        return doctors;
    }


    //Adding the patient names into an array list to retrieve and add it into the patientOption combo box
    public ArrayList<String> getPatients(){

        ArrayList<String> patients = new ArrayList<>();
        String query = "SELECT PatientId,FirstName FROM Patients";

        if(con != null){
            try{
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()){
                    patients.add(rs.getString("PatientId") + " - " + rs.getString("FirstName"));
                }
            }
            catch (Exception e){
                System.out.println("Couldn't retrieve patient data "+e.getMessage());
            }
        }
        else{
            System.out.println("Couldn't connect to database");
        }
        return patients;
    }

    //method to create Appointment
    public boolean createAppointment(int DoctorID, String DoctorName, int PatientId, String PatientName, Date AppointmentDate, String AppointmentTime, String AppointmentFee, String Description){

        String query = "INSERT INTO Appointments (DoctorID, DoctorName, PatientId, PatientName, AppointmentDate, AppointmentTime, AppointmentFee, Description) VALUES (?,?,?,?,?,?,?,?)";
        int result = 0;

        // Converting the java.util date type provided by the dateChooser into java.sql date type
        java.sql.Date sqlDate = new java.sql.Date(AppointmentDate.getTime());

        try{
        PreparedStatement prepstmt = con.prepareStatement(query);


        prepstmt.setInt(1,DoctorID);
        prepstmt.setString(2,DoctorName);
        prepstmt.setInt(3,PatientId);
        prepstmt.setString(4,PatientName);
        prepstmt.setDate(5, sqlDate);
        prepstmt.setString(6, AppointmentTime);
        prepstmt.setString(7, AppointmentFee);
        prepstmt.setString(8, Description);

         result = prepstmt.executeUpdate();


        }
        catch (SQLException e){
            System.out.println("Couldn't insert data "+e.getMessage());
        }

        return result > 0;
    }




}
