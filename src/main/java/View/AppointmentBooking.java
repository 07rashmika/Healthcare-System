package View;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AppointmentBooking extends JFrame {
    private JPanel mainPanel;
    private JPanel dateChooserPanel;
    private JComboBox doctorOption;
    private JComboBox patientOption;
    private JComboBox timeOption;
    private JButton createApp;
    private JButton updateApp;
    private JButton deleteApp;
    private JLabel manageDoctorLabel;
    private JLabel managePatientLabel;
    private JLabel manageInventoryLabel;
    private JLabel bookAppointmentLabel;
    private JLabel viewReportsLabel;
    private JTextArea description;
    private JTextField appointmentFee;
    private JTable appointmentTable;

    JDateChooser dateChooser = new JDateChooser();

    URL imageUrl = AppointmentBooking.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();

    public AppointmentBooking() {
        // Set up the frame
        setSize(1300, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setIconImage(image);


//        //Setting the Column Names
//        String[] columnNames = {"Appointment ID", "Doctor ID", "Doctor Name", "Patient ID", "Patient Name", "Date", "Time", "Appointment Fee"};
//        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//        appointmentTable.setModel(model);



        dateChooser.setPreferredSize(new Dimension(120, 30));//Size of the date picker
        dateChooserPanel.add(dateChooser); // Add the date picker to the dateChooserPanel

        setVisible(true);
    }





}
