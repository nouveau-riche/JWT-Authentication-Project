package com.example.JWT.Authentication.Project.services;

import com.example.JWT.Authentication.Project.entities.UserEntity;
import com.example.JWT.Authentication.Project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServices {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> createUser(UserEntity userEntity) {

        Map<String, Object> response = new HashMap<>();

        try {

            if (userEntity.getPassword().length() <= 5) {
                response.put("status", false);
                response.put("description", "Password should be of at least 6 characters");
                return response;
            }

            UserEntity userFoundInDB = checkUserExistsInDB(userEntity);

            if (userFoundInDB != null) {
                response.put("status", false);
                response.put("description", "User is already registered");
                return response;
            }

            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(List.of("USER"));
            userRepository.save(userEntity);

            response.put("description", "User created successfully");
            response.put("status", true);
            return response;
        } catch (Exception e) {
            response.put("status", false);
            response.put("description", String.join("Something went wrong: ", e.toString()));
            return response;
        }
    }

    public Map<String, Object> loginUser(UserEntity userEntity) {

        Map<String, Object> response = new HashMap<>();

        try {
            UserEntity userFoundInDB = checkUserExistsInDB(userEntity);

            if (userFoundInDB == null) {
                response.put("status", false);
                response.put("description", "User is not registered");
                return response;
            }

            boolean isPasswordMatch = passwordEncoder.matches(userEntity.getPassword(), userFoundInDB.getPassword());

            if (!isPasswordMatch) {
                response.put("status", false);
                response.put("description", "Incorrect password");
                return response;
            }

            response.put("status", true);
            return response;
        } catch (Exception e) {
            response.put("status", false);
            response.put("description", String.join("Something went wrong: ", e.toString()));
            return response;
        }

    }

    public UserEntity checkUserExistsInDB(UserEntity userEntity) {
        try {
            return userRepository.findByEmail(userEntity.getEmail());
        } catch (Exception e) {
            return null;
        }
    }

}
