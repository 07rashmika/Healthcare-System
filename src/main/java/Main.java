import DBController.DBConnection;
import View.AppointmentBooking;

public class Main {
    public static void main(String[] args) {
        new AppointmentBooking();
        new DBConnection().startConnection();
    }
}
