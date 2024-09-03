package com.example.JWT.Authentication.Project.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    @GetMapping("/hello")
    public String greeting() {
        return "Hello!, I am running well";
    }

}
