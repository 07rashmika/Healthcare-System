package Controller;

import DBController.DBConnection;
import Model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientManager {

    // Method to add a new patient to the database
    public static boolean addPatient(Patient patient) {
        Connection connection = DBConnection.startConnection();
        String query = "INSERT INTO Patients (PatientId, FirstName, LastName, DateOfBirth, Gender, ContactNumber, EmailAddress, Address, MedicalHistory, CreatedAt, UpdatedAt) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, patient.getPatientId());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getLastName());
            stmt.setDate(4, new java.sql.Date(patient.getDateOfBirth().getTime()));
            stmt.setString(5, patient.getGender());
            stmt.setString(6, patient.getContactNumber());
            stmt.setString(7, patient.getEmailAddress());
            stmt.setString(8, patient.getAddress());
            stmt.setString(9, patient.getMedicalHistory());

            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            stmt.setDate(10, currentDate);
            stmt.setDate(11, currentDate);

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing patient record
    public static boolean updatePatient(Patient patient) {
        Connection connection = DBConnection.startConnection();
        String query = "UPDATE Patients SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, ContactNumber = ?, " +
                "EmailAddress = ?, Address = ?, MedicalHistory = ?, UpdatedAt = ? WHERE PatientId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setDate(3, new java.sql.Date(patient.getDateOfBirth().getTime()));
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getContactNumber());
            stmt.setString(6, patient.getEmailAddress());
            stmt.setString(7, patient.getAddress());
            stmt.setString(8, patient.getMedicalHistory());
            stmt.setDate(9, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(10, patient.getPatientId());

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a patient by their ID
    public static boolean deletePatient(int patientId) {
        Connection connection = DBConnection.startConnection();
        String query = "DELETE FROM Patients WHERE PatientId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all patients from the database
    public static List<Patient> getAllPatients() {
        Connection connection = DBConnection.startConnection();
        String query = "SELECT * FROM Patients";
        List<Patient> patients = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("PatientId"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Gender"),
                        rs.getString("ContactNumber"),
                        rs.getString("EmailAddress"),
                        rs.getString("Address"),
                        rs.getString("MedicalHistory"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Method to search patients by name or ID
    public static List<Patient> searchPatients(String query) {
        Connection connection = DBConnection.startConnection();
        List<Patient> patients = new ArrayList<>();
        String searchQuery = "SELECT * FROM Patients WHERE FirstName LIKE ? OR LastName LIKE ? OR PatientId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(searchQuery)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            stmt.setInt(3, Integer.parseInt(query));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("PatientId"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Gender"),
                        rs.getString("ContactNumber"),
                        rs.getString("EmailAddress"),
                        rs.getString("Address"),
                        rs.getString("MedicalHistory"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt")
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
