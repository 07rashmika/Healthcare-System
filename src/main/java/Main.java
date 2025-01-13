import Controller.AppointmentController;

import Model.AppointmentModel;
import View.AppointmentView;
import javax.swing.*;



public class Main {
    public static void main(String[] args) {


        //NEED TO ADD A RESET BUTTON TO RESET THE APPOINTMENT FORM

        try{
            AppointmentView view = new AppointmentView();
            AppointmentModel model = new AppointmentModel();
            new AppointmentController(model,view);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error running application "+e.getMessage());
        }
    }

}

