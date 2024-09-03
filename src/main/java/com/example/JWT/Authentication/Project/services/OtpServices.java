package com.example.JWT.Authentication.Project.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class OtpServices {

    private static final int OTP_LENGTH = 6;
    private final Random random = new SecureRandom();

    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));  // Generates a digit between 0-9
        }
        return otp.toString();
    }

}
