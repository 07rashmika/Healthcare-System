import Controller.DoctorController;
import Model.DoctorModel;
import View.DoctorView;

//Main method
public class DoctorMain {
    public static void main(String[] args) {
        DoctorModel model = new DoctorModel();
        DoctorView view = new DoctorView();
        new DoctorController(model, view);

    }

}
