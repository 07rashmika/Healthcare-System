package View;

import Controller.DoctorController;
import Controller.InventoryController;
import Controller.PharmacyController;
import Controller.ReportController;
import Model.DoctorModel;
import Model.InventoryModel;
import Model.PharmacyModel;
import Model.ReportModel;
import com.toedter.calendar
        .JDateChooser;


import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class AppointmentView extends JFrame {
    private JPanel mainPanel;
    private JPanel dateChooserPanel;
    private JComboBox<String> doctorOption;
    private JComboBox<String> patientOption;
    private JButton createApp;
    private JButton updateApp;
    private JButton deleteApp;
    private JTextArea description;
    private JTextField appointmentFee;
    private JPanel tablePanel;
    private JTable appointmentTable;
    private JScrollPane appointmentScrollPane;
    private JPanel timePanel;
    private JSpinner appointmentID;
    private JButton resetBtn;
    private JButton doctorPageBtn;
    private JButton patientPageBtn;
    private JButton inventoryPageBtn;
    private JButton appointmentPageBtn;
    private JButton reportPageBtn;
    private JButton stockNotifyPageBtn;
    private JButton appointmentNotifyPageBtn;
    private JButton inventoryReportPageBtn;
    private JSpinner timeSpinner;
    private SpinnerNumberModel IDSpinnerModel;
    private JButton[] buttons = {doctorPageBtn,patientPageBtn,inventoryPageBtn,appointmentPageBtn,reportPageBtn,stockNotifyPageBtn,appointmentNotifyPageBtn,inventoryReportPageBtn};
    private JFrame frame;

    DefaultTableModel defaultTableModel = new DefaultTableModel(){
        //overrides the isCellEditable method of default table model and makes the cell non-editable
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JDateChooser dateChooser = new JDateChooser();

    //Icon for the application shows in the top left corner of the frame
    URL imageUrl = AppointmentView.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();

//---------------------------------------------CONSTRUCTOR---------------------------------------------//
    public AppointmentView() {
        // Set up the frame
        frame = new JFrame("Book Appointment");
        setTitle("Code Crew HealthCare Management System");
        frame.setSize(1380, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);//Displays the application in the middle of the screen
        frame.setIconImage(image);
        //set the time selector
        setTime();

        //setting up navigation button
        setNavigationButtons();


        //adding the datechooser into a JPanel
        setDateChooser();

        //set ID spinner
        setIDSpinner();

        //Appointment Table Columns
        createAppointmentTable();

        frame.setVisible(true);

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setValuesEmpty();
            }
        });

//---------------------------------Navigation Buttons------------------------//

        doctorPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoctorModel model = new DoctorModel();
                DoctorView view = new DoctorView();
                new DoctorController(model, view);
                frame.dispose();
            }
        });

        inventoryPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory view = new Inventory();
                InventoryModel model = new InventoryModel();

                // Pass the View and Model to the Controller
                new InventoryController(model, view);
                frame.dispose();
            }
        });

        stockNotifyPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PharmacyModel model = new PharmacyModel();
                PharmacyView view = new PharmacyView();
                new PharmacyController(model, view);
                frame.dispose();
            }
        });

        inventoryReportPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryReport();
                frame.dispose();
            }
        });

        reportPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportView reportView = new ReportView();// Explicitly calling frame.setVisible(true)
                // Create the model and controller for the ReportView
                ReportModel model = new ReportModel();
                new ReportController(model, reportView);
                frame.dispose();
            }
        });



    }
//--------------------------------------------------------------------------------------------------------//

    //setting up navigation button
    public void setNavigationButtons(){
        for(JButton button:buttons){
            button.setBorder(BorderFactory.createEmptyBorder());

            //hover effect on mouse enter
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(Color.decode("#B9E6B3"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(Color.decode("#8ADE8D"));
                }
            });
        }
    }

    //setting the ID spinner not ho below 1
    public void setIDSpinner(){
        IDSpinnerModel = new SpinnerNumberModel(1,1,999,1);
        appointmentID.setModel(IDSpinnerModel);
    }


    //Setting the date selector
    public void setDateChooser(){
        dateChooser.setMinimumSize(new Dimension(120, 30));
        dateChooser.setPreferredSize(new Dimension(120, 30));
        dateChooser.setMaximumSize(new Dimension(120, 30));//Size of the date picker
        dateChooserPanel.add(dateChooser); // Add the date picker to the dateChooserPanel
    }
    //setting the time selector calendar.hour_of_day shows the time in 24Hour mode
    public void setTime(){
        SpinnerDateModel timeChooser = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        timeSpinner = new JSpinner(timeChooser);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner,"HH:mm");
        timeSpinner.setEditor(timeEditor);

        timePanel.add(timeSpinner);
    }

//---------------------------------------------TABLE FUNCTIONALITIES---------------------------------------------//

    //Adding columns to the table
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


        appointmentTable.setModel(defaultTableModel);
        appointmentTable.setDragEnabled(false);
        appointmentTable.setRowHeight(30);

    }

    //Adding the rows data parsed as an Object parameter into the table
    public void setAppointmentTable(Object[][] data){
        for(Object[] row:data){
            defaultTableModel.addRow(row);
        }
    }
//    public DefaultTableModel getAppointmentTableModel(){
//        return defaultTableModel;
//    }
    public JTable getAppointmentTable(){
        return appointmentTable;
    }
    public void clearAppointmentTable(){
        defaultTableModel.setRowCount(0);
    }
    public int getAppointmentID(){
        return (int) defaultTableModel.getValueAt(appointmentTable.getSelectedRow(),0);
    }
    public void removeRow(){
        defaultTableModel.removeRow(appointmentTable.getSelectedRow());
    }
    public int getTotalRowCount(){
        return appointmentTable.getRowCount();
    }
    public int getSelectedRowCount(){
        return appointmentTable.getSelectedRowCount();
    }
    //Selection listener to identify that a row has been selected
    public void rowSelectionListener(ListSelectionListener listener){
        appointmentTable.getSelectionModel().addListSelectionListener(listener);
    }
    //Updates the table after an update in the selected row
    public void updateAppointmentTableRow(int row, Object[] rowData){
        for (int i = 0; i < rowData.length; i++) {
            defaultTableModel.setValueAt(rowData[i],row , i);
        }
    }

//---------------------------------------------FORM FUNCTIONALITIES(SETTERS)---------------------------------------------//

    //Sets the data from the selected JTable row into the corresponding form fields
    public void setFieldDataFromRow(Object[] data){
        setAppointmentID((int) data[0]);
        setDoctorOption(data[1] + " - " + data[2]);
        setPatientOption(data[3] + " - " + data[4]);
        setDateChooser((Date) data[5]);
        setTimeSpinner((java.sql.Time) data[6]);
        setAppointmentFee(String.valueOf(data[7]));
        setDescription( (String) data[8]);
    }
    //Setters for each form fields used to set the data selected from the JTable row
    public void setAppointmentID(int ID) {
        appointmentID.setValue(ID);
    }
    public void setDoctorOption(String doctor) {
        doctorOption.setSelectedItem(doctor);
    }
    public void setPatientOption(String patient) {
        patientOption.setSelectedItem(patient);
    }
    public void setDescription(String desc) {
        description.setText(desc);
    }
    public void setAppointmentFee(String fee) {
        appointmentFee.setText(fee);
    }
    public void setTimeSpinner(Time time) {
       timeSpinner.setValue(time);
    }
    public void setDateChooser(Date date) {
        dateChooser.setDate(date);
    }
    //set values empty after booking
    public void setValuesEmpty(){
        appointmentID.setValue(1);
        doctorOption.setSelectedIndex(0);
        patientOption.setSelectedIndex(0);
        dateChooser.setDate(null);
        appointmentFee.setText("");
        description.setText("");
    }


//---------------------------------------------ADDING FORM DETAILS(GETTERS)---------------------------------------------//


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
    //method to get AppointmentID
    public int getAppointmentIDValue(){
        return (int) appointmentID.getValue();
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
    //method to get the fee
    public String getFee(){
        return appointmentFee.getText();
    }
    //method to get the description
    public String getDescription(){
        return description.getText();
    }

//---------------------------------------------BUTTON LISTENERS---------------------------------------------//
    //Create appointment button listener
    public void createAppListener(ActionListener listener){
        createApp.addActionListener(listener);
    }

    public void deleteAppListener(ActionListener listener){
        deleteApp.addActionListener(listener);
    }

    public void updateAppListener(ActionListener listener){
        updateApp.addActionListener(listener);
    }


//---------------------------------------------END OF CODE---------------------------------------------//
}
