package Controller;


import Model.AppointmentModel;
import View.AppointmentView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class AppointmentController {
    AppointmentModel model;
    AppointmentView view;

    //---------------------------------------------CONSTRUCTOR WITH TWO PARAMETERS---------------------------------------------//
    public AppointmentController(AppointmentModel model, AppointmentView view) {
        this.model = model;
        this.view = view;

        try{
            //adding the doctor and patient details retrieved in the Appointment model into addDoctors as a parameter and as an array to loop through
            view.addDoctors(model.getDoctors().toArray(new String[0]));
            view.addPatients(model.getPatients().toArray(new String[0]));

            //load the appointment details into the table
            loadAppointments();

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(view,"Error fetching data" + e.getMessage());
        }

        //---------------------------------------------BUTTON LISTENERS---------------------------------------------//

        //Create Appointment button action listener
        view.createAppListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                bookAppointment();
                loadAppointments();
            }
        });
        //Delete Appointment button action listener
        view.deleteAppListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                deleteAppointment();
            }
        });
        //update Appointment button action listener
        view.updateAppListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAppointment();
            }
        });
        //JTable row selection listeners
        view.rowSelectionListener(action ->{
            if(!action.getValueIsAdjusting()){
                setRowFromField();
            }
        });

        //remove appointments that are finished
        removeExpiredAppointmentScheduler();

    }

//---------------------------------------------NECESSARY METHODS OUTSIDE THE CONSTRUCTOR---------------------------------------------//

    //method to BookAppointment
    private void bookAppointment(){
        try{

            //retrieving the doctor ID and doctor name from the doctor option drop down
            String[] doctorDetails = view.getSelectedDoctor().split(" - ");
            int DoctorID = Integer.parseInt(doctorDetails[0]);
            String DoctorName = doctorDetails[1];

            //retrieving the doctor ID and doctor name from the doctor option drop down
            String[] patientDetails = view.getSelectedPatient().split(" - ");
            int PatientId = Integer.parseInt(patientDetails[0]);
            String PatientName = patientDetails[1] ;


            LocalDateTime now = LocalDateTime.now();//get the system date and time
            Date AppointmentDate = view.getDate();//retrieve date
            Date AppointmentTime = view.getTime();//retrieve time
            //combine year,month,date,hour,minute together into one variable
            LocalDateTime combinedDateTime = LocalDateTime.of(
                    AppointmentDate.getYear() + 1900, //getYear returns currentYear - 1900 so we add 1900
                    AppointmentDate.getMonth() + 1,// 0 is jan and 11 is december so we add 1 to right month
                    AppointmentDate.getDate(),
                    AppointmentTime.getHours(),
                    AppointmentTime.getMinutes()
            );

            //formatting time into sql datatype time
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.sql.Time selectedTime = java.sql.Time.valueOf(timeFormat.format(AppointmentTime));

            //get the email from the patients table
            String email = model.getEmail(PatientId);

            String AppointmentFee = view.getFee();
            String AppointmentDescription = view.getDescription();


            //checking if the selected date is older than the system date/in the past
            if(combinedDateTime.isBefore(now) ){
                JOptionPane.showMessageDialog(view,"Please select a valid date");
                return;
            }


            // Validate Appointment Fee
            if (AppointmentFee == null || AppointmentFee.isEmpty()) {
                JOptionPane.showMessageDialog(view,"Please enter an Appointment Fee!!");
                return;
            }

            try {
                int fee = Integer.parseInt(AppointmentFee);
                if (fee <= 0) {
                    JOptionPane.showMessageDialog(view,"Appointment Fee can't be a negative number!!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Appointment Fee format. It must be a number.");
                return;
            }

            // Validate Appointment Description
            if (AppointmentDescription == null || AppointmentDescription.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view,"Please enter an Description!!");
                return;
            }

            boolean success = model.createAppointment(DoctorID,DoctorName,PatientId,PatientName,AppointmentDate,selectedTime,AppointmentFee,AppointmentDescription,email);//pass parameters into the model class method

            if(success){
                JOptionPane.showMessageDialog(view,"Appointment Booking Success");
                view.setValuesEmpty();
            }
            else{
                JOptionPane.showMessageDialog(view,"Appointment Booking Failed");
            }
        } catch (Exception e) {
            System.out.println("Error in appointment booking" + e.getMessage());
        }

    }

    //method to delete and appointment
    public void deleteAppointment(){

        int selectedRowCount = view.getSelectedRowCount();
        int rowCount = view.getTotalRowCount();

        try{
            if(selectedRowCount == 0){

                if(rowCount == 0){
                    JOptionPane.showMessageDialog(view,"There are no rows to delete");
                }else{
                    JOptionPane.showMessageDialog(view,"Please select a row to delete");
                }
                return;
            }
            int appointmentID = view.getAppointmentID();
            boolean success = model.removeAppointment(appointmentID);


            if(success){
                view.removeRow();
                JOptionPane.showMessageDialog(view,"Appointment removed successfully");
            }
            else{
                JOptionPane.showMessageDialog(view,"Couldn't delete Appointment");
            }
        }catch (Exception e){
            System.out.println("Error deleting appointment" + e.getMessage());
        }
    }

    //method to update appointment
    public void updateAppointment(){

        try{

            int appointmentID = view.getAppointmentID();

            String[] doctorDetails = view.getSelectedDoctor().split(" - ");
            int doctorID = Integer.parseInt(doctorDetails[0]);
            String doctorName = doctorDetails[1];

            String[] patientDetails = view.getSelectedPatient().split(" - ");
            int patientId = Integer.parseInt(patientDetails[0]);
            String patientName = patientDetails[1];

            LocalDateTime now = LocalDateTime.now();
            Date appointmentDate = view.getDate();
            Date appointmentTime = view.getTime();
            LocalDateTime combinedDateTime = LocalDateTime.of(
                    appointmentDate.getYear() + 1900,
                    appointmentDate.getMonth() + 1,
                    appointmentDate.getDate(),
                    appointmentTime.getHours(),
                    appointmentTime.getMinutes()
            );
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.sql.Time selectedTime = java.sql.Time.valueOf(timeFormat.format(appointmentTime));
            java.sql.Date selectedDate = new java.sql.Date(appointmentDate.getTime());

            String fee = view.getFee();



            String description = view.getDescription();

            if(combinedDateTime.isBefore(now)){
                JOptionPane.showMessageDialog(view,"Please select a valid date");
                return;
            }


            // Validate Appointment Fee
            if (fee == null || fee.isEmpty()) {
                JOptionPane.showMessageDialog(view,"Please enter an Appointment Fee!!");
                return;
            }

            try {
                int Fee = Integer.parseInt(fee);
                if (Fee <= 0) {
                    JOptionPane.showMessageDialog(view,"Appointment Fee can't be a negative number!!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Appointment Fee format. It must be a number.");
                return;
            }

            // Validate Appointment Description
            if (description == null || description.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view,"Please enter an Description!!");
                return;
            }

            boolean success = model.updateAppointment(appointmentID,doctorID,doctorName,patientId,patientName,appointmentDate,selectedTime,fee,description);

            if (success) {
                // Update JTable row
                int selectedRow = view.getAppointmentTable().getSelectedRow();
                view.updateAppointmentTableRow(selectedRow, new Object[]{
                        appointmentID, doctorID, doctorName, patientId, patientName, selectedDate, selectedTime, fee, description
                });
                JOptionPane.showMessageDialog(view, "Appointment updated successfully.");
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update appointment.");
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(view, "Error updating appointment: " + e.getMessage());
        }


    }

    //method to display the database table data into JTable
    public void loadAppointments(){
        List<Object[]> appointment = model.getAppointmentDetails();
        Object[][] appointmentData = appointment.toArray(new Object[0][]);
        view.clearAppointmentTable();
        view.setAppointmentTable(appointmentData);
    }

    //retrieves the data from the fields and add it into the JTable after updating a row
    private void setRowFromField(){
        int selectedRow = view.getAppointmentTable().getSelectedRow();

        if(selectedRow >= 0){
            Object[] rowData = new Object[view.getAppointmentTable().getColumnCount()];

            for(int i = 0; i<rowData.length;i++){
                rowData[i] = view.getAppointmentTable().getValueAt(selectedRow,i);
            }
            view.setFieldDataFromRow(rowData);
        }
    }

    //method to check and remove appointment if it has passed the system date and time
    public void removeExpiredAppointment(){
        int totalRowCount = view.getTotalRowCount();
        LocalDateTime currentDateTime = LocalDateTime.now();

        for(int i = totalRowCount - 1; i>=0; i--){
            Date appointmentDate = (Date) view.getAppointmentTable().getValueAt(i,5);
            Time appointmentTime = (Time)  view.getAppointmentTable().getValueAt(i,6);
            int appointmentId = (int) view.getAppointmentTableModel().getValueAt(i,0);

            LocalDateTime combinedDateTime = LocalDateTime.of(
                    appointmentDate.getYear() + 1900,
                    appointmentDate.getMonth() + 1,
                    appointmentDate.getDate(),
                    appointmentTime.getHours(),
                    appointmentTime.getMinutes()
            );

            if(combinedDateTime.isBefore(currentDateTime)){
                view.getAppointmentTableModel().removeRow(i);
                model.removeAppointment(appointmentId);
            }
        }

    }
    //method to run the removeExpiredAppointment automatically every 2 minutes
    public void removeExpiredAppointmentScheduler(){
        Timer scheduler = new Timer(120000,event ->{
            removeExpiredAppointment();
            loadAppointments();
        });
        scheduler.start();
    }


//---------------------------------------------END OF CODE---------------------------------------------//
}
