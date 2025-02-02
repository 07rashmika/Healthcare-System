package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PatientView extends JFrame {
    private JPanel mainPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField contactField;
    private JTextField emailField;
    private JTextArea addressField;
    private JTextArea medicalHistoryField;
    private JComboBox<String> genderComboBox;
    private JSpinner dobSpinner;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JTable patientTable;
    private DefaultTableModel tableModel;

    public PatientView() {
        setTitle("Patient Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Patient Details"));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        contactField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextArea(2, 20);
        medicalHistoryField = new JTextArea(2, 20);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        dobSpinner = new JSpinner(new SpinnerDateModel());
        ((JSpinner.DateEditor) dobSpinner.getEditor()).getFormat().applyPattern("yyyy-MM-dd");

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Date of Birth:"));
        formPanel.add(dobSpinner);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderComboBox);
        formPanel.add(new JLabel("Contact Number:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Email Address:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(new JScrollPane(addressField));
        formPanel.add(new JLabel("Medical History:"));
        formPanel.add(new JScrollPane(medicalHistoryField));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Table Panel
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "First Name", "Last Name", "DOB", "Gender", "Contact", "Email", "Address", "Medical History"});
        patientTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(patientTable);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);
    }

    // Getters for form inputs
    public String getFirstName() { return firstNameField.getText(); }
    public String getLastName() { return lastNameField.getText(); }
    public String getContact() { return contactField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getAddress() { return addressField.getText(); }
    public String getMedicalHistory() { return medicalHistoryField.getText(); }
    public String getGender() { return (String) genderComboBox.getSelectedItem(); }
    public Object getDateOfBirth() { return dobSpinner.getValue(); }

    // Setters for form inputs
    public void setFirstName(String text) { firstNameField.setText(text); }
    public void setLastName(String text) { lastNameField.setText(text); }
    public void setContact(String text) { contactField.setText(text); }
    public void setEmail(String text) { emailField.setText(text); }
    public void setAddress(String text) { addressField.setText(text); }
    public void setMedicalHistory(String text) { medicalHistoryField.setText(text); }
    public void setGender(String gender) { genderComboBox.setSelectedItem(gender); }
    public void setDateOfBirth(Object date) { dobSpinner.setValue(date); }

    // Table Functions
    public void addPatientRow(Object[] rowData) { tableModel.addRow(rowData); }
    public void removePatientRow(int row) { tableModel.removeRow(row); }
    public void clearTable() { tableModel.setRowCount(0); }
    public int getSelectedRow() { return patientTable.getSelectedRow(); }

    // Button Listeners
    public void addPatientListener(ActionListener listener) { addButton.addActionListener(listener); }
    public void updatePatientListener(ActionListener listener) { updateButton.addActionListener(listener); }
    public void deletePatientListener(ActionListener listener) { deleteButton.addActionListener(listener); }
    public void clearFormListener(ActionListener listener) { clearButton.addActionListener(listener); }
}
