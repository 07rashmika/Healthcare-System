package main;

  // Ensure this matches the actual package path of your Patient.java class

import View.PatientView;

public class PatientMain {
    public static void main(String[] args) {
        // Launch the Patient Management System UI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PatientView(); // Initialize the view
            }
        });
    }
}
