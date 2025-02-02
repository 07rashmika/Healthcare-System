package View;

import Controller.*;
import Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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
    private JButton stockNotifyPageBtn;
    private JButton appointmentNotifyPageBtn;
    private JButton inventoryReportPageBtn;

    private JTextField txtItemID;
    private JTextField txtItemName;
    private JSpinner spnQuantity;
    private JSpinner spnThreshold;
    private JTextField txtUnitPrice;
    private JTextField txtCategory;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    JFrame frame;

    URL imageUrl = DoctorView.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();


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

        // Instantiate the View and Model


        // Set up the JFrame
        frame = new JFrame("Code Crew Health Care Management System");
        frame.setContentPane(getMainPanel()); // Set the main panel from the View
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(1380, 720));  // Ensure the GUI has a suitable size
        frame.setLocationRelativeTo(null);              // Center the frame
        frame.setVisible(true);
        frame.setIconImage(image);

        //---------------------------------Navigation Buttons------------------------//

        btnBookAppointments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentModel model = new AppointmentModel();
                AppointmentView view = new AppointmentView();
                new AppointmentController(model,view);
                frame.dispose();
            }
        });

        appointmentNotifyPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(SendMailView::new);
            }
        });

        btnManageDoctors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoctorModel model = new DoctorModel();
                DoctorView view = new DoctorView();
                new DoctorController(model, view);
                frame.dispose();
            }
        });


        stockNotifyPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PharmacyModel model = new PharmacyModel();
                PharmacyView view = new PharmacyView();
                new PharmacyController(model, view);
                frame.dispose();
            }
        });

        inventoryReportPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryReport();
                frame.dispose();
            }
        });

        btnViewReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportView reportView = new ReportView();// Explicitly calling frame.setVisible(true)
                // Create the model and controller for the ReportView
                ReportModel model = new ReportModel();
                new ReportController(model, reportView);
                frame.dispose();
            }
        });



        // Optional: Set default focus to the main panel
        SwingUtilities.invokeLater(() -> getMainPanel().requestFocusInWindow());
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(144, 238, 144)); // Light green

        JLabel headerLabel = new JLabel("Code Crew Health Care Management System");
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

        btnManageDoctors = new JButton("Manage Doctors", new ImageIcon(getClass().getResource("/Icons/doctor-icon-2.png")));
        btnManagePatients = new JButton("Manage Patients", new ImageIcon(getClass().getResource("/Icons/patient-icon.png")));
        btnManageInventory = new JButton("Manage Inventory", new ImageIcon(getClass().getResource("/Icons/inventory-icon-2.png")));
        btnBookAppointments = new JButton("Book Appointments", new ImageIcon(getClass().getResource("/Icons/appointment-icon.png")));
        btnViewReports = new JButton("View Reports", new ImageIcon(getClass().getResource("/Icons/report-icon.png")));
        stockNotifyPageBtn = new JButton("Stock Notification", new ImageIcon(getClass().getResource("/Icons/Stock-notify-icon.png")));
        appointmentNotifyPageBtn = new JButton("Appointment Notification", new ImageIcon(getClass().getResource("/Icons/Appointment-notify-icon.png")));
        inventoryReportPageBtn = new JButton("Inventory Report", new ImageIcon(getClass().getResource("/Icons/report-icon.png")));

        panel.add(btnManageDoctors);
        panel.add(btnManagePatients);
        panel.add(btnManageInventory);
        panel.add(btnBookAppointments);
        panel.add(btnViewReports);
        panel.add(stockNotifyPageBtn);
        panel.add(appointmentNotifyPageBtn);

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