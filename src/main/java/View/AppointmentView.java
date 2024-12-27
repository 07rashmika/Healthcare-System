package View;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Date;

public class AppointmentView extends JFrame {
    private JPanel mainPanel;
    private JPanel dateChooserPanel;
    public JComboBox<String> doctorOption;
    public JComboBox<String> patientOption;
    private JButton createApp;
    private JButton updateApp;
    private JButton deleteApp;
    private JLabel manageDoctorLabel;
    private JLabel managePatientLabel;
    private JLabel manageInventoryLabel;
    private JLabel bookAppointmentLabel;
    private JLabel viewReportsLabel;
    public JTextArea description;
    public JTextField appointmentFee;
    private JTable appointmentTable;
    public JRadioButton morningRadio;
    public JRadioButton eveningRadio;

    public JDateChooser dateChooser = new JDateChooser();

    URL imageUrl = AppointmentView.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();

    public AppointmentView() {
        // Set up the frame
        setSize(1300, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setIconImage(image);

        dateChooser.setPreferredSize(new Dimension(120, 30));//Size of the date picker
        dateChooserPanel.add(dateChooser); // Add the date picker to the dateChooserPanel

//        //Setting the Column Names
//        String[] columnNames = {"Appointment ID", "Doctor ID", "Doctor Name", "Patient ID", "Patient Name", "Date", "Time", "Appointment Fee"};
//        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//        appointmentTable.setModel(model);


        setVisible(true);

    }

    //method to add doctor names into the dropdown
    public void addDoctors(String[] doctors){
        for(String doctor:doctors){
            doctorOption.addItem(doctor);
        }
    }
    //method to add patient names into the dropdown
    public void addPatients(String[] patients){
        for(String patient:patients){
            patientOption.addItem(patient);
        }
    }

    //method to get the selected doctor name
    public String getSelectedDoctor(){
        return (String) doctorOption.getSelectedItem();
    }

    //method to get the selected patient name
    public String getSelectedPatient(){
        return (String) patientOption.getSelectedItem();
    }

    //method to get the selected date
    public Date getDate(){
        return dateChooser.getDate();
    }

    //method to get the selected time
    public String getTime(){
        String time = "";
        if(morningRadio.isSelected()){
            time = "AM";
        }
        else if(eveningRadio.isSelected()){
            time = "PM";
        }
        return time;
    }

    //method to get the fee
    public String getFee(){
        return appointmentFee.getText();
    }

    //method to get the description
    public String getDescription(){
        return description.getText();
    }

    //Create appointment button listener
    public void createAppListener(ActionListener listener){
        createApp.addActionListener(listener);
    }


}
