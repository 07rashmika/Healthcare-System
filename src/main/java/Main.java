
import Controller.AppointmentController;

import Model.AppointmentModel;
import View.AppointmentView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            AppointmentModel model = new AppointmentModel();
            AppointmentView view = new AppointmentView();
            new AppointmentController(model,view);

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error running application "+e.getMessage());
        }
    }
}