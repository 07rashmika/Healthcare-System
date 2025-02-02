

import View.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            new LoginView();

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error running application "+e.getMessage());
        }
    }
}