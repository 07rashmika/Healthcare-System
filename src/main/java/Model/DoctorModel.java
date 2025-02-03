package Model;

import DBController.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorModel {
    private Connection connection;

    //creating connection
    public DoctorModel() {
        try {
            connection = DBConnection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //get the details of doctors from table
    public List<Object[]> getAllDoctors() {
        List<Object[]> doctors = new ArrayList<>();
        try {
            String query = "SELECT * FROM SA24610154_Doctors";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                doctors.add(new Object[]{
                        rs.getInt("DoctorID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Specialization"),
                        rs.getString("Gender")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
    //add doctor to the table using add doctor
    public boolean addDoctor(int ID, String firstName, String lastName, String phone, String specialization, String gender) {
        try {
            String query = "INSERT INTO SA24610154_Doctors (DoctorID, FirstName, LastName, PhoneNumber, Specialization, Gender) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, phone);
            ps.setString(5, specialization);
            ps.setString(6, gender);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //updating doctor info
    public boolean updateDoctor(int doctorID, String firstName, String lastName, String phone, String specialization, String gender) {
        try {
            String query = "UPDATE SA24610154_Doctors SET FirstName = ?, LastName = ?, PhoneNumber = ?, Specialization = ?, Gender = ? WHERE DoctorID = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phone);
            ps.setString(4, specialization);
            ps.setString(5, gender);
            ps.setInt(6, doctorID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //deleting a doctor
    public boolean deleteDoctor(int doctorID) {
        try {
            String query = "DELETE FROM SA24610154_Doctors WHERE DoctorID = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctorID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
