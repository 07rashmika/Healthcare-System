package View;




import Controller.AppointmentController;
import Controller.InventoryController;
import Controller.PharmacyController;
import Controller.ReportController;
import Model.AppointmentModel;
import Model.InventoryModel;
import Model.PharmacyModel;
import Model.ReportModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class DoctorView extends JFrame {
    private JPanel mainPanel;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnRemove;
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
    private JButton doctorPageBtn;
    private JButton patientPageBtn;
    private JButton inventoryPageBtn;
    private JButton appointmentPageBtn;
    private JButton reportPageBtn;
    private JButton stockNotifyPageBtn;
    private JButton appointmentNotifyPageBtn;
    private JButton inventoryReportPageBtn;
    private ButtonGroup genderGroup;
    private JButton[] buttons = {doctorPageBtn,patientPageBtn,inventoryPageBtn,appointmentPageBtn,reportPageBtn,stockNotifyPageBtn,appointmentNotifyPageBtn,inventoryReportPageBtn};
    private JFrame frame;


    DefaultTableModel defaultTableModel = new DefaultTableModel();


    //Icon for the application shows in the top left corner of the frame
    URL imageUrl = DoctorView.class.getResource("/Icons/app-icon.png");
    ImageIcon icon = new ImageIcon(imageUrl);
    Image image = icon.getImage();

//---------------------------------------------CONSTRUCTOR---------------------------------------------//
    public DoctorView() {
        // Set up the frame
        frame = new JFrame("Book Appointment");
        setTitle("Code Crew HealthCare Management System");
        frame.setSize(1380, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);//Displays the application in the middle of the screen
        frame.setIconImage(image);

        radioMale.setActionCommand("Male");
        radioFemale.setActionCommand("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(radioMale);
        genderGroup.add(radioFemale);

        setNavigationButtons();
        //Appointment Table Columns
        createDoctorTable();

        frame.setVisible(true);

        //---------------------------------Navigation Buttons------------------------//

        appointmentNotifyPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(SendMailView::new);
            }
        });

        appointmentPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentModel model = new AppointmentModel();
                AppointmentView view = new AppointmentView();
                new AppointmentController(model,view);
                frame.dispose();
            }
        });


        stockNotifyPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PharmacyModel model = new PharmacyModel();
                PharmacyView view = new PharmacyView();
                new PharmacyController(model, view);
                frame.dispose();
            }
        });

        patientPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PatientView();
                frame.dispose();
            }
        });

        inventoryReportPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryReport();
                frame.dispose();
            }
        });

        reportPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportView reportView = new ReportView();// Explicitly calling frame.setVisible(true)
                // Create the model and controller for the ReportView
                ReportModel model = new ReportModel();
                new ReportController(model, reportView);
                frame.dispose();
            }
        });

        inventoryPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory view = new Inventory();
                InventoryModel model = new InventoryModel();

                // Pass the View and Model to the Controller
                new InventoryController(model, view);
                frame.dispose();
            }
        });




    }
//--------------------------------------------------------------------------------------------------------//

    //setting up navigation button
    public void setNavigationButtons(){
        for(JButton button:buttons){
            button.setBorder(BorderFactory.createEmptyBorder());

            //hover effect on mouse enter
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(Color.decode("#B9E6B3"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(Color.decode("#8ADE8D"));
                }
            });
        }
    }

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
