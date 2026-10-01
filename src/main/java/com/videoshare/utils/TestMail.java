package com.videoshare.utils;

import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class TestMail {
    public static void main(String[] args) {
        String FROM_EMAIL = "thienbao.184841@gmail.com";
        String APP_PASSWORD = "bogkapkibjmbzcik";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("test@example.com"));
            message.setSubject("Test", "UTF-8");
            message.setText("Test", "UTF-8");

            Transport.send(message);
            System.out.println("SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FAILED");
        }
    }
}
