package Model;


import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import DBController.DBConnection;


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
                    //adds into the array list as   PatientId - FirstName
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

    //Retrieving Email of the patient from patients table
    public String getEmail(int patientId){
        String selectQuery = "SELECT EmailAddress FROM Patients WHERE PatientId = ?";
        String email = "";

        try{

        PreparedStatement prepstmt = con.prepareStatement(selectQuery);
        prepstmt.setInt(1,patientId);
        ResultSet rs = prepstmt.executeQuery();

        while (rs.next()){
            email = rs.getString("EmailAddress");
        }
        } catch (SQLException e) {
            System.out.println("Couldn't retrieve Email " + e.getMessage());
        }
        return email;
    }

    //boolean method to create Appointment takes needed column details as parameters, parameters are passed from the AppointmentController class
    public boolean createAppointment(int DoctorID, String DoctorName, int PatientId, String PatientName, Date AppointmentDate, java.sql.Time AppointmentTime, String AppointmentFee, String Description, String Email){


        String query = "INSERT INTO Appointments (DoctorID, DoctorName, PatientId, PatientName, AppointmentDate, AppointmentTime, AppointmentFee, Description, Email) VALUES (?,?,?,?,?,?,?,?,?)";
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
        prepstmt.setTime(6, AppointmentTime);
        prepstmt.setString(7, AppointmentFee);
        prepstmt.setString(8, Description);
        prepstmt.setString(9, Email);

         result = prepstmt.executeUpdate();



        }
        catch (SQLException e){
            System.out.println("Couldn't insert data "+e.getMessage());
        }

        return result > 0;//returns true if any rows have been affected
    }

    //method to get all the appointment details from the appointments table
    public List<Object[]> getAppointmentDetails(){

        List<Object[]> appointmentDetails = new ArrayList<>();
        String query = "SELECT * FROM Appointments";


        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);//returns a set of items

                while(rs.next()){
                    appointmentDetails.add(new Object[]{
                            rs.getInt("AppointmentID"),
                            rs.getInt("DoctorID"),
                            rs.getString("DoctorName"),
                            rs.getInt("PatientId"),
                            rs.getString("PatientName"),
                            rs.getDate("AppointmentDate"),
                            rs.getTime("AppointmentTime"),
                            rs.getInt("AppointmentFee"),
                            rs.getString("Description"),
                            rs.getString("Email")
                        }
                    );
                }


        }
        catch(SQLException e){
            System.out.println("Couldn't get appointment details" + e.getMessage());
        }

        return appointmentDetails;
    }

    //method to remove an appointment
    public boolean removeAppointment(int appointmentID){

        String query = "DELETE FROM Appointments WHERE AppointmentID = ?";
        int result = 0;

        try{
            PreparedStatement prepstmt = con.prepareStatement(query);
            prepstmt.setInt(1,appointmentID);
            result = prepstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Couldn't delete row " + e.getMessage());
        }

        return result > 0;
    }

    //boolean method to update an appointment
    public boolean updateAppointment(int AppointmentID, int DoctorID, String DoctorName, int PatientId, String PatientName, Date AppointmentDate, java.sql.Time AppointmentTime, String AppointmentFee, String Description){


        String query = "UPDATE Appointments SET DoctorID = ?, DoctorName = ?, PatientId = ?, PatientName = ?, AppointmentDate = ?, AppointmentTime = ?, AppointmentFee = ?, Description = ? WHERE AppointmentId = ? ";
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
            prepstmt.setTime(6, AppointmentTime);
            prepstmt.setString(7, AppointmentFee);
            prepstmt.setString(8, Description);
            prepstmt.setInt(9, AppointmentID);


            result = prepstmt.executeUpdate();



        }
        catch (SQLException e){
            System.out.println("Couldn't Update data "+e.getMessage());
        }

        return result > 0;//returns true if any rows have been affected
    }


//---------------------------------------------END OF CODE---------------------------------------------//
}
