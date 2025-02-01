package View;

import Controller.InventoryReportController;
import Model.InventoryItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class InventoryReport extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton manageDoctorsButton;
    private JButton managePatientsButton;
    private JButton manageInventoryButton;
    private JButton bookAppointmentsButton;
    private JButton revenuePatientVisitsButton;
    private JButton inventoryUsageButton;
    private JButton buttonClear;
    private JTable InventoryReportTable;
    private JComboBox<String> CategoryCombo;
    private JComboBox<String> ComboMonth;
    private JComboBox<String> ComboYear;
    private JButton generateButton;
    private InventoryReportController controller;
    DefaultTableModel defaultTableModel = new DefaultTableModel();

    public InventoryReport() {
        controller = new InventoryReportController();

        setTitle("Inventory Report");
        setSize(1300, 720);
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);

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
