import Controller.AppointmentController;
import DBController.DBConnection;
import Model.AppointmentModel;
import View.AppointmentView;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {


        try{
            AppointmentView view = new AppointmentView();
            AppointmentModel model = new AppointmentModel();
             new AppointmentController(model,view);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error running application"+e.getMessage());
        }

    }

}

