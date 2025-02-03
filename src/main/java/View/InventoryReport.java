package View;

import Controller.*;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class InventoryReport extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton doctorPageBtn;
    private JButton patientPageBtn;
    private JButton inventoryPageBtn;
    private JButton appointmentPageBtn;
    private JButton reportPageBtn;
    private JButton inventoryReportPageBtn;
    private JButton buttonClear;
    private JTable InventoryReportTable;
    private JComboBox<String> CategoryCombo;
    private JComboBox<String> ComboMonth;
    private JComboBox<String> ComboYear;
    private JButton generateButton;
    private JButton stockNotifyPageBtn;
    private JButton appointmentNotifyPageBtn;
    private InventoryReportController controller;
    DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JFrame frame;

    public InventoryReport() {
        controller = new InventoryReportController();
        frame = new JFrame("Inventory Report");
        frame.setSize(1380, 720);
        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setDefaultButton(buttonOK);
        frame.setVisible(true);

        // Load categories from the database
        loadCategories();
        loadMonths();
        loadYears();
        createReportInventoryTable();

        // Event Listeners
        generateButton.addActionListener(e -> generateReport());
        buttonClear.addActionListener(e -> clearTable());
        buttonOK.addActionListener(e -> dispose());

        // Window Close Event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // Escape Key to Close
        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

//---------------------------------Navigation Buttons------------------------//

        doctorPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoctorModel model = new DoctorModel();
                DoctorView view = new DoctorView();
                new DoctorController(model, view);
                frame.dispose();
            }
        });

        patientPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PatientView();
                frame.dispose();
            }
        });

        inventoryPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory view = new Inventory();
                InventoryModel model = new InventoryModel();

                // Pass the View and Model to the Controller
                new InventoryController(model, view);
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

        reportPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportView reportView = new ReportView();// Explicitly calling frame.setVisible(true)
                // Create the model and controller for the ReportView
                ReportModel model = new ReportModel();
                new ReportController(model, reportView);
                frame.dispose();
            }
        });
        appointmentPageBtn.addActionListener(new ActionListener() {
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
    }

    public void createReportInventoryTable(){
        defaultTableModel.addColumn("Category");
        defaultTableModel.addColumn("Item ID");
        defaultTableModel.addColumn("Quantity");

        InventoryReportTable.setModel(defaultTableModel);
        InventoryReportTable.setRowHeight(30);

        InventoryReportTable.revalidate();
        InventoryReportTable.repaint();
    }

    private void loadCategories() {
        DefaultTableModel model = (DefaultTableModel) InventoryReportTable.getModel();

        String[] columnNames = {"Category", "Item ID", "Quantity"};
        model.setColumnIdentifiers(columnNames);

        List<String> categories = controller.getCategories();
        for (String category : categories) {
            CategoryCombo.addItem(category);
        }
    }

    private void loadMonths() {
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        for (String month : months) {
            ComboMonth.addItem(month);
        }
    }

    private void loadYears() {
        for (int year = 2020; year <= 2030; year++) {
            ComboYear.addItem(String.valueOf(year));
        }
    }

    private void generateReport() {
        String selectedCategory = (String) CategoryCombo.getSelectedItem();
        String selectedMonth = (String) ComboMonth.getSelectedItem();
        String selectedYear = (String) ComboYear.getSelectedItem();

        if (selectedCategory == null || selectedMonth == null || selectedYear == null) return;

        List<InventoryItem> report = controller.getReportByCategoryAndDate(selectedCategory, selectedMonth, selectedYear);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Category");
        model.addColumn("Item ID");
        model.addColumn("Stock Quantity");

        for (InventoryItem item : report) {
            model.addRow(new Object[]{
                    item.getCategory(),
                    item.getItemID(),
                    item.getStockQuantity()
            });
        }

        InventoryReportTable.setModel(model);
    }

    private void clearTable() {
        InventoryReportTable.setModel(new DefaultTableModel());
    }

}
