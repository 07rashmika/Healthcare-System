import javax.swing.JOptionPane;
import controller.ReportController;
import model.ReportModel;
import view.ReportView;

public class Main {
    public static void main(String[] args) {
        try {
            // Create an instance of ReportView and make it visible
            ReportView reportView = new ReportView();
            reportView.frame.setVisible(true); // Explicitly calling frame.setVisible(true)

            // Create the model and controller for the ReportView
            ReportModel model = new ReportModel();
            new ReportController(model, reportView); // Pass model and view to controller

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error running application: " + e.getMessage());
        }
    }
}
