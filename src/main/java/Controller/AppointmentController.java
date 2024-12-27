package Controller;

import DBController.DBConnection;
import Model.AppointmentModel;
import View.AppointmentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

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


    }

    private void bookAppointment(){
        //retrieving the doctor ID and doctor name from the doctor option drop down
        String[] doctorDetails = view.getSelectedDoctor().split(" - ");
        int DoctorID = Integer.parseInt(doctorDetails[0]);
        String DoctorName = doctorDetails[1];

        //retrieving the doctor ID and doctor name from the doctor option drop down
        String[] patientDetails = view.getSelectedPatient().split(" - ");
        int PatientId = Integer.parseInt(patientDetails[0]);
        String PatientName =patientDetails[1] ;

        Date sysDate = new Date();
        Date AppointmentDate = view.getDate();
        String AppointmentTime = view.getTime();
        String AppointmentFee = view.getFee();
        String AppointmentDescription = view.getDescription();


        if(AppointmentDate.before(sysDate)){
            JOptionPane.showMessageDialog(view,"Selected date is older than the system date");
            return;
        }

        boolean success = model.createAppointment(DoctorID,DoctorName,PatientId,PatientName,AppointmentDate,AppointmentTime,AppointmentFee,AppointmentDescription);

        if(success){
            JOptionPane.showMessageDialog(view,"Appointment Booking Success");
            view.eveningRadio.setText(null);
            view.doctorOption.setSelectedIndex(0);
            view.patientOption.setSelectedIndex(0);
            view.appointmentFee.setText("");
            view.description.setText("");
            view.dateChooser.setDate(null);
        }
        else{
            JOptionPane.showMessageDialog(view,"Appointment Booking Failed");
        }

    }






}
