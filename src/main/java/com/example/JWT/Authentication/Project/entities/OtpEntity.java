package com.example.JWT.Authentication.Project.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpEntity {

    @Email
    @NotBlank(message = "Field 'email' is required")
    private String email;

}
