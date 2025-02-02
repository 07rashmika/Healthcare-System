package Controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Properties;

public class SendMail {

    private static final String EMAIL_USERNAME = "helloworldsahan@gmail.com";
    private static final String EMAIL_PASSWORD = "xmmpaqeyuxtyeyhy";  // Use App Password here

    public static void sendAppointmentNotification(String patientEmail, String patientName, String appointmentId, String location, String doctorName) {
        String subject = "Appointment Reminder - MedicarePlus";
        String body = "Dear " + patientName + ",\n\n"
                + "This is a friendly reminder about your upcoming appointment.\n"
                + "Please make sure to arrive on time.\n\n"
                + "Appointment Details:\n"
                + "- Appointment ID  : " + appointmentId + "\n"
                + "- Doctor  : " + doctorName + "\n"
                + "- Location  : " + location + "\n"
                + "- Date & Time : Please refer to your appointment schedule.\n\n"
                + "If you have any questions, feel free to contact us at " + EMAIL_USERNAME + ".\n\n"
                + "Thank you for choosing MedicarePlus. We look forward to serving you.\n\n"
                + "Best regards,\n"
                + "MedicarePlus Team";

        sendEmail(patientEmail, subject, body);
    }

    private static void sendEmail(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Email sent successfully to " + recipientEmail,
                    "Success", JOptionPane.INFORMATION_MESSAGE));

        } catch (MessagingException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Failed to send email. Check console for details.",
                    "Error", JOptionPane.ERROR_MESSAGE));
        }
    }

//    public static void main(String[] args) {
//        sendAppointmentNotification("sdwickkramaarachchi@gmail.com", "sahan", "1","madama","hhhh") ;
//    }
}
