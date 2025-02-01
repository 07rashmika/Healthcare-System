import View.InventoryReport;

import javax.swing.*;

public class InventoryReportMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryReport frame = new InventoryReport();
            frame.setVisible(true);
        });
    }
}