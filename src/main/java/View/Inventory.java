package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Inventory {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;

    private JButton btnManageDoctors;
    private JButton btnManagePatients;
    private JButton btnManageInventory;
    private JButton btnBookAppointments;
    private JButton btnViewReports;

    private JTextField txtItemID;
    private JTextField txtItemName;
    private JSpinner spnQuantity;
    private JSpinner spnThreshold;
    private JTextField txtUnitPrice;
    private JTextField txtCategory;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;

    public Inventory() {
        // Initialize components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = createHeaderPanel();
        sidebarPanel = createSidebarPanel();
        contentPanel = createContentPanel();

        // Assemble panels
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(144, 238, 144)); // Light green

        JLabel headerLabel = new JLabel("MedicarePlus Health Care Management System");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/app-icon.png"))); // Load icon

        panel.add(headerLabel);
        return panel;
    }

    private JPanel createSidebarPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(152, 251, 152)); // Light green
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        btnManageDoctors = new JButton("Manage Doctors", new ImageIcon(getClass().getResource("/Icons/doctor-icon.png")));
        btnManagePatients = new JButton("Manage Patients", new ImageIcon(getClass().getResource("/Icons/patient-icon.png")));
        btnManageInventory = new JButton("Manage Inventory");
        btnBookAppointments = new JButton("Book Appointments");
        btnViewReports = new JButton("View Reports");

        panel.add(btnManageDoctors);
        panel.add(btnManagePatients);
        panel.add(btnManageInventory);
        panel.add(btnBookAppointments);
        panel.add(btnViewReports);

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel sectionTitle = new JLabel("Manage Pharamcy Inventory");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 20));
        sectionTitle.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(sectionTitle, BorderLayout.NORTH);

        formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Item ID:"));
        txtItemID = new JTextField();
        formPanel.add(txtItemID);

        formPanel.add(new JLabel("Item Name:"));
        txtItemName = new JTextField();
        formPanel.add(txtItemName);

        formPanel.add(new JLabel("Quantity:"));
        spnQuantity = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        formPanel.add(spnQuantity);

        formPanel.add(new JLabel("Threshold Quantity:"));
        spnThreshold = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        formPanel.add(spnThreshold);

        formPanel.add(new JLabel("Unit Price:"));
        txtUnitPrice = new JTextField();
        formPanel.add(txtUnitPrice);

        formPanel.add(new JLabel("Category:"));
        txtCategory = new JTextField();
        formPanel.add(txtCategory);

        panel.add(formPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAdd = new JButton("Add to Inventory");
        btnAdd.setBackground(new Color(144, 238, 144));
        btnUpdate = new JButton("Update Inventory");
        btnUpdate.setBackground(new Color(255, 223, 0));
        btnDelete = new JButton("Delete Item");
        btnDelete.setBackground(new Color(255, 99, 71));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Getter methods for buttons and fields (used by Controller)
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTextField getTxtItemID() { return txtItemID; }
    public JTextField getTxtItemName() { return txtItemName; }
    public JSpinner getSpnQuantity() { return spnQuantity; }
    public JSpinner getSpnThreshold() { return spnThreshold; }
    public JTextField getTxtUnitPrice() { return txtUnitPrice; }
    public JTextField getTxtCategory() { return txtCategory; }
}