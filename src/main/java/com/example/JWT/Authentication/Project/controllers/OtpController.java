package com.example.JWT.Authentication.Project.controllers;

import com.example.JWT.Authentication.Project.entities.OtpEntity;
import com.example.JWT.Authentication.Project.services.EmailServices;
import com.example.JWT.Authentication.Project.services.OtpServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class OtpController {

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private OtpServices otpServices;

    @PostMapping("/otp")
    public Map<String, Object> sendOtp(@Valid @RequestBody OtpEntity otpEntity) {

        Map<String, Object> response = new HashMap<>();

        try {

            String otp = otpServices.generateOtp();
            emailServices.sendMail(otpEntity.getEmail(), MessageFormat.format("Otp for JWT Springboot Application is {0}", otp), "Sending mail from springboot server");

            response.put("status", true);
            response.put("otp", otp);
            response.put("description", "Otp sent successfully");
            return response;
        } catch (Exception e) {
            response.put("status", false);
            response.put("description", "Something went wrong");
            return response;
        }

    }

}
