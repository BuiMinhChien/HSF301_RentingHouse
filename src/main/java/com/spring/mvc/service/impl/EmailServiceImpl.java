package com.spring.mvc.service.impl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import com.spring.mvc.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "emailService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailServiceImpl implements EmailService {

    private final String username;
    private final String password;
    private final Properties props;

    // Constructor để khởi tạo các giá trị email
    public EmailServiceImpl(String username, String password) {
        this.username = username;
        this.password = password;

        // Thiết lập cấu hình SMTP cho email
        this.props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email to " + toEmail);
        }
    }
}
