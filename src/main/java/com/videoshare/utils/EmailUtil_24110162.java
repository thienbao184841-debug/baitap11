package com.videoshare.utils;

import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil_24110162 {
    // Email và App Password bạn vừa cung cấp
    private static final String FROM_EMAIL = "thienbao.184841@gmail.com";
    private static final String APP_PASSWORD = "bogkapkibjmbzcik"; // Lưu ý bỏ khoảng trắng nếu JavaMail báo lỗi, nhưng
                                                                   // thường để nguyên vẫn ok

    public static boolean sendOTP(String toEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // JavaMail có thể không nhận khoảng trắng, an toàn nhất là xóa bỏ khoảng trắng
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD.replace(" ", ""));
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP Xác Nhận Đăng Ký Tài Khoản VideoShare", "UTF-8");

            String content = "Chào bạn,\n\n"
                    + "Bạn đang đăng ký tài khoản tại VideoShare.\n"
                    + "Mã OTP kích hoạt tài khoản của bạn là: " + otp + "\n\n"
                    + "Vui lòng nhập mã này vào trang xác nhận để hoàn tất đăng ký.\n"
                    + "Trân trọng!";
            message.setText(content, "UTF-8");

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
