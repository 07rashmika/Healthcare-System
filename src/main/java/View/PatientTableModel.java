package View;

import Model.Patient;
import javax.swing.table.DefaultTableModel;  // Ensure this matches the actual package path of your Patient.java class

public class PatientTableModel extends DefaultTableModel {
    private static final String[] COLUMN_NAMES = {"PatientId", "FirstName", "LastName", "DateOfBirth", "Gender", "ContactNumber", "EmailAddress", "Address", "MedicalHistory", "CreatedAt", "UpdatedAt"};

    public PatientTableModel() {
        super(COLUMN_NAMES, 0);
    }

    public void addRow(Patient patient) {
        super.addRow(new Object[]{
                patient.getPatientId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getContactNumber(),
                patient.getEmailAddress(),
                patient.getAddress(),
                patient.getMedicalHistory(),
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        });
    }
}
