package View;

import Model.Medication;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PharmacyView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JTextField textField1;

    public PharmacyView() {
        setTitle("Low Stock Medications");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupUI();
    }

    private void setupUI() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Stock", "Threshold"}, 0);
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new StockHighlightRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

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
