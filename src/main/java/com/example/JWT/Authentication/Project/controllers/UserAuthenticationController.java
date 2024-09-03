package com.example.JWT.Authentication.Project.controllers;

import com.example.JWT.Authentication.Project.entities.UserEntity;
import com.example.JWT.Authentication.Project.jwt.JwtUtils;
import com.example.JWT.Authentication.Project.repositories.CustomUserDetailService;
import com.example.JWT.Authentication.Project.services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserEntity userEntity) {

        try {
            Map<String, Object> response;

            response = userServices.createUser(userEntity);

            if (response.get("status").equals(true)) {
//                String jwtToken = authenticateAndGenerateJwt(userEntity);
//                response.put("token", jwtToken);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("description", String.join("Something went wrong: ", exception.toString()));
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserEntity userEntity) {

        Map<String, Object> response;

        try {
            response = userServices.loginUser(userEntity);

            if (response.get("status").equals(false)) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            String jwtToken = authenticateAndGenerateJwt(userEntity);

            response.put("description", "Login successfully");
            response.put("token", jwtToken);
            response.put("status", true);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("description", String.join("Something went wrong: ", exception.toString()));
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

    }

    private String authenticateAndGenerateJwt(UserEntity userEntity) {

        try {
            Authentication authentication;

            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailService.loadUserByUsername(userEntity.getUsername());
            return jwtUtils.generateTokenFromUsername(userDetails);

        } catch (Exception e) {
            System.out.println("nikunj error in jwt");
            System.out.println(e);
            return null;
        }

    }

}
