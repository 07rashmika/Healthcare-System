package View;

import javax.swing.*;

public class PatientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PatientView().setVisible(true);
        });
    }
}
