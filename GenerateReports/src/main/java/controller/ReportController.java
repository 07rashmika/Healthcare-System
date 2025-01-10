package controller;

import model.ReportModel;
import view.ReportView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class ReportController {

    private final ReportModel model;
    private final ReportView view;

    public ReportController(ReportModel model, ReportView view) {
        this.model = model;
        this.view = view;

        // Action for the Generate Revenue Report button
        view.getGenerateRevenueReportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRevenueReport();
            }
        });

        // Action for the Generate Patient Visit Report button
        view.getGenerateVisitReportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateVisitReport();
            }
        });

        // Action for the Reset button
        view.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetReport();
            }
        });
    }

    // Method to generate the Revenue Report
    private void generateRevenueReport() {
        int month = view.getMonthSelection(); // Get selected month
        int year = view.getYearSelection();  // Get selected year

        List<Object[]> appointmentDetails = model.getAppointmentDetailsForMonthAndYear(month, year);
        double totalRevenue = 0.0;

        // Clear previous data in the table
        view.clearTable();

        for (Object[] appointment : appointmentDetails) {
            // Add data to the table
            view.addRowToTable(appointment);
            // Sum the revenue
            totalRevenue += (double) appointment[7]; // Assuming AppointmentFee is at index 7
        }

        // Set the total revenue label
        view.setTotalRevenue(totalRevenue);
    }

    // Method to generate the Patient Visit Report
    private void generateVisitReport() {
        int month = view.getMonthSelection(); // Get selected month
        int year = view.getYearSelection();  // Get selected year

        List<Object[]> appointmentDetails = model.getAppointmentDetailsForMonthAndYear(month, year);
        int totalVisits = appointmentDetails.size();

        // Clear previous data in the table
        view.clearTable();

        for (Object[] appointment : appointmentDetails) {
            // Add data to the table
            view.addRowToTable(appointment);
        }

        // Set the total visit count label
        view.setVisitCount(totalVisits);
    }

    // Method to reset the report
    private void resetReport() {
        view.clearTable();
        view.resetLabels();
    }
}
