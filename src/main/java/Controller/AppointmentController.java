package Controller;

import DBController.DBConnection;
import Model.AppointmentModel;
import View.AppointmentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentController {
    AppointmentModel model;
    AppointmentView view;


    public AppointmentController(AppointmentModel model, AppointmentView view) {
        this.model = model;
        this.view = view;

        try{
        //adding the doctor and patient details retrieved in the Appointment model into addDoctors as a parameter and as an array to loop through
        view.addDoctors(model.getDoctors().toArray(new String[0]));
        view.addPatients(model.getPatients().toArray(new String[0]));

        //load the appointment details into the table
//        loadAppointments();


        //adding the appointment Details to the table
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(view,"Error fetching data" + e.getMessage());
        }

        //Create Appointment button action listener
        view.createAppListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                bookAppointment();
            }
        });

        view.deleteAppListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                deleteAppointment();
            }
        });


            List<Object[]> appointment = model.getAppointmentDetails();
            Object[][] appointmentData = appointment.toArray(new Object[0][]);
            view.setAppointmentTable(appointmentData);

    }

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

            Date sysDate = new Date();
            Date AppointmentDate = view.getDate();
            Date AppointmentTime = view.getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.sql.Time selectedTime = java.sql.Time.valueOf(timeFormat.format(AppointmentTime));

            //get the email from the patients table
            String email = model.getEmail(PatientId);

            String AppointmentFee = view.getFee();
            String AppointmentDescription = view.getDescription();


            //checking if the selected date is older than the system date/in the past
            if( AppointmentDate == null || AppointmentDate.before(sysDate) ){
                JOptionPane.showMessageDialog(view,"Please select a valid date");
                return;
            }

            // Validate Email (Assuming model.getEmail() returns null for invalid IDs)

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



        boolean success = model.createAppointment(DoctorID,DoctorName,PatientId,PatientName,AppointmentDate,selectedTime,AppointmentFee,AppointmentDescription,email);

        if(success){
            JOptionPane.showMessageDialog(view,"Appointment Booking Success");
            view.setValuesEmpty();
        }
        else{
            JOptionPane.showMessageDialog(view,"Appointment Booking Failed");
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteAppointment(){
        int appointmentID = view.getAppointmentID();

        boolean success = model.removeAppointment(appointmentID);
        if(success){
            view.removeRow();
            JOptionPane.showMessageDialog(view,"Appointment removed successfully");
        }
        else{
            JOptionPane.showMessageDialog(view,"Couldn't delete Appointment");
        }
    }



//    public void loadAppointments(){
//        List<Object[]> appointment = model.getAppointmentDetails();
//        Object[][] appointmentData = appointment.toArray(new Object[0][]);
//        view.setAppointmentTable(appointmentData);
//    }




}
