package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.AppointmentController;
import Controller.LoginController;
import Model.AppointmentModel;

public class LoginView extends JFrame {
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JFrame frame;

    public LoginView() {
        frame = new JFrame();
        frame.setContentPane(contentPane);
        frame.setTitle("Hospital Management System - Login");
        frame.setSize(880, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (LoginController.login(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful");

                    frame.dispose();
                    AppointmentModel model = new AppointmentModel();
                    AppointmentView view = new AppointmentView();
                    new AppointmentController(model,view);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }
}
