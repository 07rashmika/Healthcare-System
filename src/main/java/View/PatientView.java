package View;

import Controller.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientView {
    private JFrame frame;
    private JTextField txtPatientId, txtFirstName, txtLastName, txtDateOfBirth, txtGender, txtContactNumber, txtEmailAddress, txtAddress, txtMedicalHistory;
    private JButton btnAddPatient, btnUpdate, btnDelete, btnSearch;
    private JTable patientTable;
    private PatientManager patientManager;
    private JButton[] navButtons = new JButton[8];
    JPanel navPanel;
    JLabel navTitle;

    public PatientView() {
        patientManager = new PatientManager();
        frame = new JFrame("Patient Management System");
        frame.setSize(1380, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

         navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(9, 1, 5, 5)); // GridLayout for navigation buttons
        navPanel.setPreferredSize(new Dimension(200, 600)); // Width of the navigation bar
        navPanel.setBackground(new Color(70, 130, 180));

        navTitle = new JLabel("Navigation", SwingConstants.CENTER);
        navTitle.setForeground(Color.WHITE);
        navTitle.setFont(new Font("Arial", Font.BOLD, 16));
        navPanel.add(navTitle);

        String[] pageNames = {"Report", "Doctor", "Send Mail", "Patient", "Appointment", "Inventory", "Inventory Report"};

        // Create navigation buttons and assign actions
        for (int i = 0; i < 7; i++) {
            navButtons[i] = new JButton(pageNames[i]); // Set proper page name
            navButtons[i].setForeground(Color.WHITE);
            navButtons[i].setBackground(new Color(30, 144, 255)); // Dodger Blue
            navButtons[i].setFocusPainted(false);
            final int pageIndex = i + 1;
            navButtons[i].addActionListener(e -> navigateToPage(pageIndex)); // Navigate to respective page
            navPanel.add(navButtons[i]);
        }


        // Create fields and buttons for adding or updating a patient
        JPanel inputPanel = new JPanel(new GridLayout(10, 2));

        inputPanel.add(new JLabel("Patient ID:"));
        txtPatientId = new JTextField();
        inputPanel.add(txtPatientId);

        inputPanel.add(new JLabel("First Name:"));
        txtFirstName = new JTextField();
        inputPanel.add(txtFirstName);

        inputPanel.add(new JLabel("Last Name:"));
        txtLastName = new JTextField();
        inputPanel.add(txtLastName);

        inputPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        txtDateOfBirth = new JTextField();
        inputPanel.add(txtDateOfBirth);

        inputPanel.add(new JLabel("Gender (M/F):"));
        txtGender = new JTextField();
        inputPanel.add(txtGender);

        inputPanel.add(new JLabel("Contact Number:"));
        txtContactNumber = new JTextField();
        inputPanel.add(txtContactNumber);

        inputPanel.add(new JLabel("Email Address:"));
        txtEmailAddress = new JTextField();
        inputPanel.add(txtEmailAddress);

        inputPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        inputPanel.add(txtAddress);

        inputPanel.add(new JLabel("Medical History:"));
        txtMedicalHistory = new JTextField();
        inputPanel.add(txtMedicalHistory);

        btnAddPatient = new JButton("Add Patient");
        inputPanel.add(btnAddPatient);

        // Create buttons for update, delete, and search functionality
        JPanel actionPanel = new JPanel();
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        actionPanel.add(btnSearch);
        actionPanel.add(btnUpdate);
        actionPanel.add(btnDelete);

        // Table to display patient records
        patientTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(patientTable);

        // Layout organization
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(actionPanel, BorderLayout.CENTER);
        frame.add(tableScrollPane, BorderLayout.SOUTH);

        // Load all patients initially
        loadPatients();

        frame.add(navPanel, BorderLayout.EAST);
        // Button Actions
        btnAddPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatient();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatients();
            }
        });

        frame.setVisible(true);
    }

    private void loadPatients() {
        List<Patient> patients = patientManager.getAllPatients();
        Object[][] data = new Object[patients.size()][11];
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            data[i][0] = patient.getPatientId();
            data[i][1] = patient.getFirstName();
            data[i][2] = patient.getLastName();
            data[i][3] = patient.getDateOfBirth();
            data[i][4] = patient.getGender();
            data[i][5] = patient.getContactNumber();
            data[i][6] = patient.getEmailAddress();
            data[i][7] = patient.getAddress();
            data[i][8] = patient.getMedicalHistory();
            data[i][9] = patient.getCreatedAt();
            data[i][10] = patient.getUpdatedAt();
        }
        String[] columns = {"PatientId", "FirstName", "LastName", "DateOfBirth", "Gender", "ContactNumber", "EmailAddress", "Address", "MedicalHistory", "CreatedAt", "UpdatedAt"};
        patientTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    private void navigateToPage(int pageNumber) {
        frame.dispose(); // Close the current ReportView window

        switch (pageNumber) {
            case 1:
                ReportView reportView = new ReportView();// Explicitly calling frame.setVisible(true)
                // Create the model and controller for the ReportView
                ReportModel model = new ReportModel();
                new ReportController(model, reportView); // ReportView
                break;
            case 2:
                DoctorModel Doctormodel = new DoctorModel();
                DoctorView view = new DoctorView();
                new DoctorController(Doctormodel, view); // DoctorView
                break;
            case 3:
                new SendMailView(); // SendMailView
                break;
            case 4:
                new PatientView(); // PatientView
                break;
            case 5:
                AppointmentModel Apointmentmodel = new AppointmentModel();
                AppointmentView Appointmentview = new AppointmentView();
                new AppointmentController(Apointmentmodel,Appointmentview);  // AppointmentView
                break;
            case 6:
                Inventory Inventoryview = new Inventory();
                InventoryModel Inventorymodel = new InventoryModel();

                // Pass the View and Model to the Controller
                new InventoryController(Inventorymodel, Inventoryview); // InventoryView
                break;
            case 7:
                new InventoryReport(); // InventoryReport
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Invalid Page");
        }
    }

    private void addPatient() {
        int patientId = Integer.parseInt(txtPatientId.getText());
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dateOfBirth = txtDateOfBirth.getText();
        String gender = txtGender.getText();
        String contactNumber = txtContactNumber.getText();
        String emailAddress = txtEmailAddress.getText();
        String address = txtAddress.getText();
        String medicalHistory = txtMedicalHistory.getText();
        Patient newPatient = new Patient(patientId, firstName, lastName, java.sql.Date.valueOf(dateOfBirth), gender, contactNumber, emailAddress, address, medicalHistory, new java.util.Date(), new java.util.Date());
        if (patientManager.addPatient(newPatient)) {
            loadPatients();
        }
    }

    private void updatePatient() {
        int patientId = Integer.parseInt(txtPatientId.getText());
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dateOfBirth = txtDateOfBirth.getText();
        String gender = txtGender.getText();
        String contactNumber = txtContactNumber.getText();
        String emailAddress = txtEmailAddress.getText();
        String address = txtAddress.getText();
        String medicalHistory = txtMedicalHistory.getText();
        Patient updatedPatient = new Patient(patientId, firstName, lastName, java.sql.Date.valueOf(dateOfBirth), gender, contactNumber, emailAddress, address, medicalHistory, new java.util.Date(), new java.util.Date());
        if (patientManager.updatePatient(updatedPatient)) {
            loadPatients();
        }
    }

    private void deletePatient() {
        int patientId = Integer.parseInt(txtPatientId.getText());
        if (patientManager.deletePatient(patientId)) {
            loadPatients();
        }
    }

    private void searchPatients() {
        String query = txtPatientId.getText();
        List<Patient> searchResults = patientManager.searchPatients(query);
        Object[][] data = new Object[searchResults.size()][11];
        for (int i = 0; i < searchResults.size(); i++) {
            Patient patient = searchResults.get(i);
            data[i][0] = patient.getPatientId();
            data[i][1] = patient.getFirstName();
            data[i][2] = patient.getLastName();
            data[i][3] = patient.getDateOfBirth();
            data[i][4] = patient.getGender();
            data[i][5] = patient.getContactNumber();
            data[i][6] = patient.getEmailAddress();
            data[i][7] = patient.getAddress();
            data[i][8] = patient.getMedicalHistory();
            data[i][9] = patient.getCreatedAt();
            data[i][10] = patient.getUpdatedAt();
        }
        String[] columns = {"PatientId", "FirstName", "LastName", "DateOfBirth", "Gender", "ContactNumber", "EmailAddress", "Address", "MedicalHistory", "CreatedAt", "UpdatedAt"};
        patientTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }


}
