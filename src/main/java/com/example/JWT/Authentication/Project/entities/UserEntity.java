package com.example.JWT.Authentication.Project.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private ObjectId id;

    private String username;

    @NotBlank(message = "Field 'email' is missing")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Field 'password' is missing")
    private String password;

    private List<String> roles;

}
