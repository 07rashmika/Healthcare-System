package Controller;

import Model.ReportModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import View.ReportView;

public class ReportController {

    private final ReportModel Model;
    private final ReportView View;

    public ReportController(ReportModel model, ReportView view) {
        this.Model = model;
        this.View = view;

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
        int month = View.getMonthSelection(); // Get selected month
        int year = View.getYearSelection();  // Get selected year

        List<Object[]> appointmentDetails = Model.getAppointmentDetailsForMonthAndYear(month, year);
        double totalRevenue = 0.0;

        // Clear previous data in the table
        View.clearTable();

        for (Object[] appointment : appointmentDetails) {
            // Add data to the table
            View.addRowToTable(appointment);
            // Sum the revenue
            totalRevenue += (double) appointment[7]; // Assuming AppointmentFee is at index 7
        }

        // Set the total revenue label
        View.setTotalRevenue(totalRevenue);
    }

    // Method to generate the Patient Visit Report
    private void generateVisitReport() {
        int month = View.getMonthSelection(); // Get selected month
        int year = View.getYearSelection();  // Get selected year

        List<Object[]> appointmentDetails = Model.getAppointmentDetailsForMonthAndYear(month, year);
        int totalVisits = appointmentDetails.size();

        // Clear previous data in the table
        View.clearTable();

        for (Object[] appointment : appointmentDetails) {
            // Add data to the table
            View.addRowToTable(appointment);
        }

        // Set the total visit count label
        View.setVisitCount(totalVisits);
    }

    // Method to reset the report
    private void resetReport() {
        View.clearTable();
        View.resetLabels();
    }
}
