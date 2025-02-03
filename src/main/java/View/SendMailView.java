package View;

import DBController.DBConnection;
import Controller.SendMail;
import DBController.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SendMailView extends JFrame {

    private final JTextField appointmentIdField;
    private final JTextField patientNameField;
    private final JTextField appointmentLocationField;
    private final JButton sendButton;

    public SendMailView() {
        super("MedicarePlus Appointment Notification");

        // Set up components
        appointmentIdField = new JTextField(20);
        patientNameField = new JTextField(20);
        appointmentLocationField = new JTextField(20);
        sendButton = new JButton("Send Notification");

        // Layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Add components to the panel with labels
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Appointment ID:"), constraints);


        constraints.gridx = 1;
        panel.add(appointmentIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Patient Name:"), constraints);

        constraints.gridx = 1;
        panel.add(patientNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Appointment Location:"), constraints);

        constraints.gridx = 1;
        panel.add(appointmentLocationField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(sendButton, constraints);

        // Add panel to frame
        add(panel);

        // Set up button action listener
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String appointmentId = appointmentIdField.getText().trim();
                String patientName = patientNameField.getText().trim();
                String appointmentLocation = appointmentLocationField.getText().trim();

                if (appointmentId.isEmpty() || patientName.isEmpty() || appointmentLocation.isEmpty()) {
                    JOptionPane.showMessageDialog(SendMailView.this,
                            "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    new Thread(() -> {
                        try (Connection conn = DBConnection.startConnection();
                             PreparedStatement stmt = conn.prepareStatement(
                                     "SELECT a.Email, p.FirstName, p.LastName, d.FirstName AS DoctorFirstName, d.LastName AS DoctorLastName " +
                                             "FROM Appointments a " +
                                             "JOIN Patients p ON a.PatientId = p.PatientId " +
                                             "JOIN SA24610154_Doctors d ON a.DoctorID = d.DoctorID " +
                                             "WHERE a.AppointmentID = ?")) {

                            stmt.setInt(1, Integer.parseInt(appointmentId));
                            ResultSet rs = stmt.executeQuery();

                            if (rs.next()) {
                                String patientEmail = rs.getString("Email");
                                String doctorName = rs.getString("DoctorFirstName") + " " + rs.getString("DoctorLastName");

                                SendMail.sendAppointmentNotification(patientEmail, patientName, appointmentId, appointmentLocation, doctorName);

                                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(SendMailView.this,
                                        "Email sent successfully to " + patientEmail, "Success", JOptionPane.INFORMATION_MESSAGE));

                            } else {
                                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(SendMailView.this,
                                        "Appointment ID not found.", "Error", JOptionPane.ERROR_MESSAGE));
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(SendMailView.this,
                                    "An error occurred while retrieving data.", "Error", JOptionPane.ERROR_MESSAGE));
                        }
                    }).start();
                }
            }
        });

        // Frame visibility settings of jpanel
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }


}

