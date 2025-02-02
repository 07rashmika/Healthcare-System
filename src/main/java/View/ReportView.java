package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ReportView {

    public JFrame frame;
    private JTable appointmentTable;
    private JButton generateRevenueReportButton;
    private JButton generateVisitReportButton;
    private JButton resetButton;
    private JButton goBackButton;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JLabel totalRevenueLabel;
    private JLabel visitCountLabel;

    // Navigation buttons
    private JButton[] navButtons = new JButton[8];

    // Constructor
    public ReportView() {
        frame = new JFrame("Report View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(1380, 720));

        // Create Navigation Panel (Left Side)
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(9, 1, 5, 5)); // GridLayout for navigation buttons
        navPanel.setPreferredSize(new Dimension(200, 0)); // Width of the navigation bar
        navPanel.setBackground(new Color(70, 130, 180)); // Steel Blue background

        JLabel navTitle = new JLabel("Navigation", SwingConstants.CENTER);
        navTitle.setForeground(Color.WHITE);
        navTitle.setFont(new Font("Arial", Font.BOLD, 16));
        navPanel.add(navTitle);

        // Navigation button labels
        String[] pageNames = {"Report", "Doctor", "Send Mail", "Patient", "Appointment", "Inventory", "Inventory Report"};

        // Create navigation buttons and assign actions
        for (int i = 0; i < 7; i++) {
            navButtons[i] = new JButton(pageNames[i]); // Set proper page name
            navButtons[i].setForeground(Color.WHITE);
            navButtons[i].setBackground(new Color(30, 144, 255)); // Dodger Blue
            navButtons[i].setFocusPainted(false);
            final int pageIndex = i + 1;
            navButtons[i].addActionListener(e -> navigateToPage(pageIndex)); // Navigate to respective page
            navPanel.add(navButtons[i]);
        }

        frame.add(navPanel, BorderLayout.WEST); // Add nav panel to the left side

        // Table to display appointment details
        appointmentTable = new JTable();
        String[] columnNames = {"Appointment ID", "DoctorID", "DoctorName", "PatientId", "PatientName", "AppointmentDate", "AppointmentTime", "AppointmentFee", "Description", "Email"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        appointmentTable.setModel(model);
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);

        // Set table header background color to light green
        JTableHeader tableHeader = appointmentTable.getTableHeader();
        tableHeader.setBackground(new Color(144, 238, 144));

        frame.add(tableScrollPane, BorderLayout.CENTER);

        // Panel for month and year selection
        JPanel topPanel = new JPanel();
        monthComboBox = new JComboBox<>();
        yearComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        for (int i = 2020; i <= 2030; i++) {
            yearComboBox.addItem(i);
        }
        topPanel.add(new JLabel("Month:"));
        topPanel.add(monthComboBox);
        topPanel.add(new JLabel("Year:"));
        topPanel.add(yearComboBox);
        frame.add(topPanel, BorderLayout.NORTH);

        // Buttons for generating reports, resetting, and going back
        JPanel buttonPanel = new JPanel();
        generateRevenueReportButton = new JButton("Generate Revenue Report");
        generateVisitReportButton = new JButton("Generate Patient Visit Report");
        resetButton = new JButton("Reset");
        goBackButton = new JButton("Go Back");

        // Set background color for buttons
        generateRevenueReportButton.setBackground(Color.BLUE);
        generateVisitReportButton.setBackground(Color.BLUE);
        resetButton.setBackground(Color.RED);
        goBackButton.setBackground(new Color(144, 238, 144)); // Light green

        // Set button text color
        generateRevenueReportButton.setForeground(Color.WHITE);
        generateVisitReportButton.setForeground(Color.WHITE);
        resetButton.setForeground(Color.WHITE);
        goBackButton.setForeground(Color.BLACK);

        buttonPanel.add(generateRevenueReportButton);
        buttonPanel.add(generateVisitReportButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(goBackButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Labels to display total revenue and visit count
        JPanel infoPanel = new JPanel();
        totalRevenueLabel = new JLabel("Total Revenue: Rs. 0.00");
        visitCountLabel = new JLabel("Total Visits: 0");

        // Set fonts
        totalRevenueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        visitCountLabel.setFont(new Font("Arial", Font.BOLD, 16));

        infoPanel.setPreferredSize(new Dimension(200, 0));

        infoPanel.add(totalRevenueLabel);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(visitCountLabel);
        frame.add(infoPanel, BorderLayout.EAST);

        // Add action listener for Go Back button
        goBackButton.addActionListener(e -> {
            frame.dispose();
            new AppointmentView(); // Navigate back to AppointmentView
        });

        frame.pack();
        frame.setVisible(true);
    }

    // Navigation method for different pages
    private void navigateToPage(int pageNumber) {
        frame.dispose(); // Close the current ReportView window

        switch (pageNumber) {
            case 1:
                new ReportView(); // ReportView
                break;
            case 2:
                new DoctorView(); // DoctorView
                break;
            case 3:
                new SendMailView(); // SendMailView
                break;
            case 4:
                new AppointmentView(); // PatientView
                break;
            case 5:
                new AppointmentView(); // AppointmentView
                break;
            case 6:
                new Inventory(); // InventoryView
                break;
            case 7:
                new InventoryReport(); // InventoryReport
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Invalid Page");
        }
    }

    public int getMonthSelection() {
        return (Integer) monthComboBox.getSelectedItem();
    }

    public int getYearSelection() {
        return (Integer) yearComboBox.getSelectedItem();
    }

    public void addRowToTable(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.addRow(row);
    }

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);
    }

    public void setTotalRevenue(double totalRevenue) {
        totalRevenueLabel.setText("Total Revenue: Rs." + totalRevenue);
    }

    public void setVisitCount(int visitCount) {
        visitCountLabel.setText("Total Visits: " + visitCount);
    }

    public JButton getGenerateRevenueReportButton() {
        return generateRevenueReportButton;
    }

    public JButton getGenerateVisitReportButton() {
        return generateVisitReportButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getGoBackButton() {
        return goBackButton;
    }

    public void resetLabels() {
        totalRevenueLabel.setText("Total Revenue: Rs. 0.00");
        visitCountLabel.setText("Total Visits: 0");
    }
}
