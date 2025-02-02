package Controller;

import DBController.DBConnection;
import Model.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientManager {

    private final Connection connection;

    public PatientManager() {
        this.connection = DBConnection.startConnection();
    }

    // Add a new patient
    public void addPatient(Patient patient) {
        String query = "INSERT INTO Patients (FirstName, LastName, DateOfBirth, Gender, ContactNumber, EmailAddress, Address, MedicalHistory, CreatedAt, UpdatedAt) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setDate(3, Date.valueOf(patient.getDateOfBirth())); // Convert LocalDate to SQL Date
            statement.setString(4, patient.getGender());
            statement.setString(5, patient.getContactNumber());
            statement.setString(6, patient.getEmailAddress());
            statement.setString(7, patient.getAddress());
            statement.setString(8, patient.getMedicalHistory());
            statement.setTimestamp(9, Timestamp.valueOf(patient.getCreatedAt())); // Convert LocalDateTime to SQL Timestamp
            statement.setTimestamp(10, Timestamp.valueOf(patient.getUpdatedAt()));

            statement.executeUpdate();

            // Get generated patient ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                patient.setPatientID(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all patients
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patients";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Patient patient = new Patient(
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getDate("DateOfBirth").toLocalDate(), // Convert SQL Date to LocalDate
                        resultSet.getString("Gender"),
                        resultSet.getString("ContactNumber"),
                        resultSet.getString("EmailAddress"),
                        resultSet.getString("Address"),
                        resultSet.getString("MedicalHistory")
                );
                patient.setPatientID(resultSet.getInt("PatientID"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
