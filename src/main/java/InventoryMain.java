
import Controller.InventoryController;
import Model.InventoryModel;
import View.Inventory;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class InventoryMain {
    private static final Logger LOGGER = Logger.getLogger(InventoryMain.class.getName());

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryMain::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        try {
            // Set system Look and Feel for better appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.warning("Failed to set Look and Feel: " + e.getMessage());
        }

        try {
            // Instantiate the View and Model
            Inventory view = new Inventory();
            InventoryModel model = new InventoryModel();

            // Pass the View and Model to the Controller
            new InventoryController(model, view);

            // Set up the JFrame
            JFrame frame = new JFrame("Code Crew Health Care Management System");
            frame.setContentPane(view.getMainPanel()); // Set the main panel from the View
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setMinimumSize(new Dimension(900, 600));  // Ensure the GUI has a suitable size
            frame.setLocationRelativeTo(null);              // Center the frame
            frame.setVisible(true);

            // Optional: Set default focus to the main panel
            SwingUtilities.invokeLater(() -> view.getMainPanel().requestFocusInWindow());
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize MVC components: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Application failed to start due to an internal error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}