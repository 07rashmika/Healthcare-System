package Controller;

import Model.Medication;
import Model.PharmacyModel;
import View.PharmacyView;

import javax.swing.*;
import java.util.List;

public class PharmacyController {
    private PharmacyModel model;
    private PharmacyView view;

    public PharmacyController(PharmacyModel model, PharmacyView view) {
        this.model = model;
        this.view = view;

        view.setRefreshAction(this::updateTable);
        updateTable();
    }

    private void updateTable() {
        List<Model.Medication> medications = model.getLowStockMedications();
        view.updateTable(medications);

        if (!medications.isEmpty()) {
            JOptionPane.showMessageDialog(view, "⚠️ Low stock detected! Check the table.", "Stock Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PharmacyController(new PharmacyModel(), new PharmacyView()).view.setVisible(true));
    }
}
