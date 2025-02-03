package View;

import Controller.AppointmentController;
import Controller.DoctorController;
import Controller.InventoryController;
import Controller.ReportController;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;


public class PharmacyView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JTextField textField1;
    private JButton[] navButtons = new JButton[8];
    private JFrame frame;
    JPanel navPanel;
    JLabel navTitle;


    URL imageUrl = AppointmentView.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();

    public PharmacyView() {
        frame = new JFrame();
        frame.setTitle("Low Stock Medications");
        frame.setSize(1380, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setIconImage(image);
        setupUI();

        navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(9, 1, 5, 5)); // GridLayout for navigation buttons
        navPanel.setPreferredSize(new Dimension(200, 0)); // Width of the navigation bar
        navPanel.setBackground(new Color(70, 130, 180)); // Steel Blue background

        navTitle = new JLabel("Navigation", SwingConstants.CENTER);
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

        frame.add(navPanel, BorderLayout.WEST);
    }

    private void setupUI() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Stock", "Threshold"}, 0);
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new StockHighlightRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh");
        add(refreshButton, BorderLayout.SOUTH);
    }

    public void setRefreshAction(Runnable action) {
        refreshButton.addActionListener(e -> action.run());
    }

    public void updateTable(List<Medication> medications) {
        tableModel.setRowCount(0);
        for (Medication med : medications) {
            tableModel.addRow(new Object[]{med.getId(), med.getName(), med.getStock(), med.getThreshold()});
        }
    }
    private void navigateToPage(int pageNumber) {
        frame.dispose(); // Close the current ReportView window

        switch (pageNumber) {
            case 1:
                ReportView reportView = new ReportView();// Explicitly calling frame.setVisible(true)
                // Create the model and controller for the ReportView
                ReportModel model = new ReportModel();
                new ReportController(model, reportView); // ReportView
                break;
            case 2:
                DoctorModel Doctormodel = new DoctorModel();
                DoctorView view = new DoctorView();
                new DoctorController(Doctormodel, view); // DoctorView
                break;
            case 3:
                new SendMailView(); // SendMailView
                break;
            case 4:
                new PatientView(); // PatientView
                break;
            case 5:
                AppointmentModel Apointmentmodel = new AppointmentModel();
                AppointmentView Appointmentview = new AppointmentView();
                new AppointmentController(Apointmentmodel,Appointmentview);  // AppointmentView
                break;
            case 6:
                Inventory Inventoryview = new Inventory();
                InventoryModel Inventorymodel = new InventoryModel();

                // Pass the View and Model to the Controller
                new InventoryController(Inventorymodel, Inventoryview); // InventoryView
                break;
            case 7:
                new InventoryReport(); // InventoryReport
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Invalid Page");
        }
    }

    private static class StockHighlightRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            int stock = Integer.parseInt(table.getModel().getValueAt(row, 2).toString());
            int threshold = Integer.parseInt(table.getModel().getValueAt(row, 3).toString());

            if (stock < threshold) {
                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }
}
