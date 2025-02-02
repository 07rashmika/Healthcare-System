package View;




import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class DoctorView extends JFrame {
    private JPanel mainPanel;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnRemove;
    private JLabel manageDoctorLabel;
    private JLabel managePatientLabel;
    private JLabel manageInventoryLabel;
    private JLabel bookAppointmentLabel;
    private JLabel viewReportsLabel;
    private JPanel tablePanel;
    private JTable doctorTable;
    private JScrollPane doctorScrollPane;
    private JRadioButton radioMale;
    private JRadioButton radioFemale;
    private JTextField txtID;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtSpecialization;
    private JTextField txtPhone;
    private JButton btnReset;
    private ButtonGroup genderGroup;




    DefaultTableModel defaultTableModel = new DefaultTableModel();


    //Icon for the application shows in the top left corner of the frame
    URL imageUrl = DoctorView.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();

//---------------------------------------------CONSTRUCTOR---------------------------------------------//
    public DoctorView() {
        // Set up the frame
        setTitle("Code Crew HealthCare Management System");
        setSize(1300, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);//Displays the application in the middle of the screen
        setIconImage(image);

        radioMale.setActionCommand("Male");
        radioFemale.setActionCommand("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(radioMale);
        genderGroup.add(radioFemale);


        //Appointment Table Columns
        createDoctorTable();

        setVisible(true);

    }
//--------------------------------------------------------------------------------------------------------//


//---------------------------------------------TABLE FUNCTIONALITIES---------------------------------------------//

    //Adding columns to the table
    public void createDoctorTable(){
        defaultTableModel.addColumn("DoctorID");
        defaultTableModel.addColumn("FirstName");
        defaultTableModel.addColumn("LastName");
        defaultTableModel.addColumn("PhoneNumber");
        defaultTableModel.addColumn("Specialization");
        defaultTableModel.addColumn("Gender");


        doctorTable.setModel(defaultTableModel);
        doctorTable.setRowHeight(30);

    }


    public JTextField getDoctorIDField() {
        return txtID;
    }

    public JTextField getFirstNameField() {
        return txtFirstName;
    }

    public JTextField getLastNameField() {
        return txtLastName;
    }

    public JTextField getPhoneField() {
        return txtPhone;
    }

    public JTextField getSpecializationField() {
        return txtSpecialization;
    }

    public JTable getDoctorTable() {
        return doctorTable;
    }
    public JButton getResetButton() {
        return btnReset;
    }

    public JButton getAddButton() {
        return btnAdd;
    }

    public JButton getUpdateButton() {
        return btnUpdate;
    }

    public JButton getDeleteButton() {
        return btnRemove;
    }

    public ButtonGroup getGenderButtonGroup() {
        return genderGroup;
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //Reset All values
    public void resetValues(){
        txtID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        genderGroup.clearSelection();
        txtSpecialization.setText("");
        txtPhone.setText("");
    }


//---------------------------------------------END OF CODE---------------------------------------------//
}
