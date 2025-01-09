package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ReportView {

    public JFrame frame;
    private JTable appointmentTable;
    private JButton generateRevenueReportButton;
    private JButton generateVisitReportButton;
    private JButton resetButton;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JLabel totalRevenueLabel;
    private JLabel visitCountLabel;

    public ReportView() {
        frame = new JFrame("Report View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Table to display appointment details
        appointmentTable = new JTable();
        String[] columnNames = {"Appointment Number", "Patient Name", "Doctor Name", "Appointment Date", "Fee"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 0 rows initially
        appointmentTable.setModel(model);
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);

        // Set table header background color to light green
        JTableHeader tableHeader = appointmentTable.getTableHeader();
        tableHeader.setBackground(new Color(144, 238, 144));  // Light green color


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

        // Buttons for generating reports and resetting
        JPanel buttonPanel = new JPanel();
        generateRevenueReportButton = new JButton("Generate Revenue Report");
        generateVisitReportButton = new JButton("Generate Patient Visit Report");
        resetButton = new JButton("Reset");

        // Set the background color for the buttons
        generateRevenueReportButton.setBackground(Color.BLUE);
        generateVisitReportButton.setBackground(Color.BLUE);
        resetButton.setBackground(Color.RED);

        // Set button text color to white for better contrast
        generateRevenueReportButton.setForeground(Color.WHITE);
        generateVisitReportButton.setForeground(Color.WHITE);
        resetButton.setForeground(Color.WHITE);

        buttonPanel.add(generateRevenueReportButton);
        buttonPanel.add(generateVisitReportButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Labels to display total revenue and visit count
        JPanel infoPanel = new JPanel();
        totalRevenueLabel = new JLabel("Total Revenue: Rs. 0.00");
        visitCountLabel = new JLabel("Total Visits: 0");

        // Set fonts for labels
        totalRevenueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        visitCountLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Set preferred size for the info panel (right column)
        infoPanel.setPreferredSize(new Dimension(200, 0));  // Adjust width as needed

        infoPanel.add(totalRevenueLabel);
        infoPanel.add(Box.createVerticalStrut(20)); // Adds space between labels
        infoPanel.add(visitCountLabel);
        frame.add(infoPanel, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
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

    public void resetLabels() {
        totalRevenueLabel.setText("Total Revenue: Rs. 0.00");
        visitCountLabel.setText("Total Visits: 0");
    }
}