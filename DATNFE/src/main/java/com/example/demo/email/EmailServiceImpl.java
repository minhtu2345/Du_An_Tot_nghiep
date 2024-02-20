package com.example.demo.email;

import com.example.demo.email.service.EmailService;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangDao;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    MimeMessage mimeMessage(String emailNhan, String tieuDe, String noiDung) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(emailNhan);
            helper.setSubject(tieuDe);
            helper.setText(noiDung,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void sendOtpDangKy(String emailNhan) {
        String tieuDe = "OTP";
        String otp = genOtp();
        String noiDung = "<html><body style='font-family: Arial, sans-serif;'>" +
                "<p>Xin chào,</p>" +
                "<p>Quý khách đang đăng ký tài khoản tại <a href='http://localhost:8080/trangchu'>http://localhost:8080/trangchu</a></p>" +
                "<p><strong style='font-size: 18px;'>OTP của bạn là " + otp + "</strong></p>" +
                "<p><em>Mã xác thực này sẽ hết hiệu lực trong 5 phút.</em></p>" +
                "<p>Để đảm bảo an toàn, vui lòng không chia sẻ mã này cho bất cứ ai.</p>" +
                "</body></html>";
        MimeMessage mimeMessage = mimeMessage(emailNhan, tieuDe, noiDung);
        otpMap.put(emailNhan, new OTP(emailNhan, otp, System.currentTimeMillis()));
        emailSender.send(mimeMessage);
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private KhachHangDao khachHangDao;

    @Override
    public void sendOtpQuenMk(String emailNhan) {
        String tieuDe = "OTP";
        String otp = genOtp();
        String noiDung = "<html><body style='font-family: Arial, sans-serif;'>" +
                "<p>Xin chào,</p>" +
                "<p>Quý khách đang yêu cầu khôi phục mật khẩu tài khoản tại <a href='http://localhost:8080/trangchu'>http://localhost:8080/trangchu</a></p>" +
                "<p><strong style='font-size: 18px;'>OTP của bạn là " + otp + "</strong></p>" +
                "<p><em>Mã xác thực này sẽ hết hiệu lực trong 5 phút.</em></p>" +
                "<p>Để đảm bảo an toàn, vui lòng không chia sẻ mã này cho bất cứ ai.</p>" +
                "</body></html>";
        MimeMessage mimeMessage = mimeMessage(emailNhan, tieuDe, noiDung);
        otpMap.put(emailNhan, new OTP(emailNhan, otp, System.currentTimeMillis()));
        emailSender.send(mimeMessage);
    }

    @Override
    public void sendPass(String emailNhan) {
        String tieuDe = "OTP";
        String pass=genPass();
        String noiDung = "<html><body style='font-family: Arial, sans-serif;'>" +
                "<p>Xin chào,</p>" +
                "<p>Quý khách đang yêu cầu khôi phục mật khẩu tài khoản tại <a href='http://localhost:8080/trangchu'>http://localhost:8080/trangchu</a></p>" +
                "<p><strong style='font-size: 18px;'>Mật khẩu mới của bạn là của bạn là " + pass + "</strong></p>" +
                "<p>Để đảm bảo an toàn, vui lòng không chia sẻ mã này cho bất cứ ai.</p>" +
                "</body></html>";
        KhachHang khachHang=khachHangDao.getKhByEmail(emailNhan);
        khachHang.setMatkhau(passwordEncoder.encode(pass));
        khachHangDao.save(khachHang);
        MimeMessage mimeMessage = mimeMessage(emailNhan, tieuDe, noiDung);
        emailSender.send(mimeMessage);
    }

    Map<String, OTP> otpMap = new HashMap<>();
    final Integer length = 6;//do dai otp

    String genOtp() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
            otp.append(digit);
        }
        return otp.toString();
    }

    // Khai báo các ký tự không chứa dấu cách
    final String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    // Tạo đối tượng Random
    final Random random = new Random();
    // Tạo phương thức để sinh chuỗi ngẫu nhiên 8 kí tự
    String genPass() {
        StringBuilder randomString = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }


    private final long fiveMinutesInMillis = 5 * 60 * 1000; // 5 phút trong mili giây
    @Override
    public Boolean isValidOtp(String email, String otp) {
        OTP Otp = otpMap.get(email);
        if (otp.equals(Otp.getOtp())) {
            long currentTime = System.currentTimeMillis();
            if (Otp.getCreationTime() + fiveMinutesInMillis >= currentTime) {
                return true;
            }
        }
        return false;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class OTP {
        private String email; // ID của người dùng
        private String otp; // Mã OTP
        private long creationTime; // Thời gian tạo OTP
    }
}
