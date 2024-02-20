package com.example.demo.email.service;

import org.springframework.stereotype.Service;


public interface EmailService {
    void sendOtpDangKy(String emailNhan);
    void sendOtpQuenMk(String emailNhan);
    void sendPass(String emailNhan);
    Boolean isValidOtp(String email,String otp);
}
