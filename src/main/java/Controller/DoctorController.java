package Controller;

import Model.DoctorModel;
import View.DoctorView;

import javax.swing.table.DefaultTableModel;

public class DoctorController {
    private DoctorModel model;
    private DoctorView view;

    //constructor
    public DoctorController(DoctorModel model, DoctorView view) {
        this.model = model;
        this.view = view;
        initialController();
    }

    private void initialController() {
        view.getAddButton().addActionListener(e -> {
            addDoctor();
            view.resetValues();
        });
        view.getUpdateButton().addActionListener(e -> updateDoctor());
        view.getDeleteButton().addActionListener(e -> deleteDoctor());
        view.getResetButton().addActionListener(e -> {
            view.resetValues(); // Call the resetValues method
        });
        loadDoctorTable();
    }

    //updating the table
    private void loadDoctorTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getDoctorTable().getModel();
        tableModel.setRowCount(0); // Clear the table
        for (Object[] doctor : model.getAllDoctors()) {
            tableModel.addRow(doctor);
        }
    }

    //adding doctor
    private void addDoctor() {
        int ID = Integer.parseInt(view.getDoctorIDField().getText()) ;
        String firstName = view.getFirstNameField().getText();
        String lastName = view.getLastNameField().getText();
        String phone = view.getPhoneField().getText();
        String specialization = view.getSpecializationField().getText();
        String gender = view.getGenderButtonGroup().getSelection().getActionCommand();

        if (model.addDoctor(ID, firstName, lastName, phone, specialization, gender)) {
            loadDoctorTable();
        } else {
            view.showErrorMessage("Failed to add doctor!");
        }
    }

    //updating doctor
    private void updateDoctor() {
        try {
            int doctorID = Integer.parseInt(view.getDoctorIDField().getText());
            String firstName = view.getFirstNameField().getText();
            String lastName = view.getLastNameField().getText();
            String phone = view.getPhoneField().getText();
            String specialization = view.getSpecializationField().getText();
            String gender = view.getGenderButtonGroup().getSelection().getActionCommand();

            if (model.updateDoctor(doctorID, firstName, lastName, phone, specialization, gender)) {
                loadDoctorTable();
            } else {
                view.showErrorMessage("Failed to update doctor!");
            }
        } catch (NumberFormatException e) {
            view.showErrorMessage("Invalid Doctor ID!");
        }
    }

    //deleting doctor
    private void deleteDoctor() {
        try {
            int doctorID = Integer.parseInt(view.getDoctorIDField().getText());
            if (model.deleteDoctor(doctorID)) {
                loadDoctorTable();
            } else {
                view.showErrorMessage("Failed to delete doctor!");
            }
        } catch (NumberFormatException e) {
            view.showErrorMessage("Invalid Doctor ID!");
        }
    }
}
