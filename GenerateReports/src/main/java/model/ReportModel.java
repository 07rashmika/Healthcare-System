package model;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportModel {

    private final Connection con = DBConnection.startConnection();

    // Fetch appointments for a specific month and year
    public List<Object[]> getAppointmentDetailsForMonthAndYear(int month, int year) {
        List<Object[]> appointmentDetails = new ArrayList<>();
        String query = "SELECT * FROM Appointments WHERE MONTH(AppointmentDate) = ? AND YEAR(AppointmentDate) = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                appointmentDetails.add(new Object[]{
                        rs.getInt("AppointmentID"),
                        rs.getInt("DoctorID"),
                        rs.getString("DoctorName"),
                        rs.getInt("PatientId"),
                        rs.getString("PatientName"),
                        rs.getDate("AppointmentDate"),
                        rs.getTime("AppointmentTime"),
                        rs.getDouble("AppointmentFee"),
                        rs.getString("Description"),
                        rs.getString("Email")
                });
            }

        } catch (SQLException e) {
            System.out.println("Couldn't get appointment details for month and year: " + e.getMessage());
        }

        return appointmentDetails;
    }
}
