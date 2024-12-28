package View;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JSpinnerDateEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Time;
import java.util.Date;

public class AppointmentView extends JFrame {
    private JPanel mainPanel;
    private JPanel dateChooserPanel;
    private JComboBox<String> doctorOption;
    private JComboBox<String> patientOption;
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
    private JRadioButton morningRadio;
    private JRadioButton eveningRadio;
    private JPanel tablePanel;
    private JTable appointmentTable;
    private JScrollPane appointmentScrollPane;
    private JPanel timePanel;
    private JSpinner timeSpinner;



    DefaultTableModel defaultTableModel = new DefaultTableModel();
    ButtonGroup appointmentTime = new ButtonGroup();
    JDateChooser dateChooser = new JDateChooser();

    URL imageUrl = AppointmentView.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();




    public AppointmentView() {
        // Set up the frame
        setTitle("Code Crew HealthCare Management System");
        setSize(1300, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setIconImage(image);

        //set the time selector
        setTime();

        //Grouping the two radio buttons
        groupRadio();

        //adding the datechooser into a JPanel
        setDateChooser();

        //Appointment Table Columns
        createAppointmentTable();


        setVisible(true);

    }

    public void setTime(){
        SpinnerDateModel timeChooser = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY);
        timeSpinner = new JSpinner(timeChooser);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner,"HH");
        timeSpinner.setEditor(timeEditor);

        timePanel.add(timeSpinner);
    }


    public void createAppointmentTable(){
        defaultTableModel.addColumn("AppointmentID");
        defaultTableModel.addColumn("DoctorID");
        defaultTableModel.addColumn("DoctorName");
        defaultTableModel.addColumn("PatientId");
        defaultTableModel.addColumn("PatientName");
        defaultTableModel.addColumn("AppointmentDate");
        defaultTableModel.addColumn("AppointmentTime");
        defaultTableModel.addColumn("AppointmentFee");
        defaultTableModel.addColumn("Description");
        defaultTableModel.addColumn("Email");

        appointmentTable.setModel(defaultTableModel);
        appointmentTable.setRowHeight(30);

    }

    public void setAppointmentTable(Object[][] data){
        for(Object[] row:data){
            defaultTableModel.addRow(row);
        }
    }

    public int getAppointmentID(){
        return (int) defaultTableModel.getValueAt(appointmentTable.getSelectedRow(),0);
    }

    public void removeRow(){
        defaultTableModel.removeRow(appointmentTable.getSelectedRow());
    }


    public void setDateChooser(){
        dateChooser.setMinimumSize(new Dimension(120, 30));
        dateChooser.setPreferredSize(new Dimension(120, 30));
        dateChooser.setMaximumSize(new Dimension(120, 30));//Size of the date picker
        dateChooserPanel.add(dateChooser); // Add the date picker to the dateChooserPanel
    }

    public void groupRadio(){
        appointmentTime.add(morningRadio);
        appointmentTime.add(eveningRadio);
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
    public Date getTime(){
        return (Date) timeSpinner.getValue();
    }

    //method to get the selected time
//    public String getTime(){
//        String time = "";
//        if(morningRadio.isSelected()){
//            time = "AM";
//        }
//        else if(eveningRadio.isSelected()){
//            time = "PM";
//        }
//        return time;
//    }

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

    public void deleteAppListener(ActionListener listener){
        deleteApp.addActionListener(listener);
    }

    //set values empty after booking
    public void setValuesEmpty(){
        doctorOption.setSelectedIndex(0);
        patientOption.setSelectedIndex(0);
        dateChooser.setDate(null);
        appointmentTime.clearSelection();
        appointmentFee.setText("");
        description.setText("");
    }

}
