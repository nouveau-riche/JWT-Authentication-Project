package com.example.JWT.Authentication.Project.controllers;

import com.example.JWT.Authentication.Project.entities.MovieEntity;
import com.example.JWT.Authentication.Project.services.MovieServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/movies")
@RestController
public class MoviesController {

    @Autowired
    private MovieServices movieServices;

    @PostMapping("/create")
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieEntity movieEntity) {

        Map<String, Object> response;

        response = movieServices.saveMovie(movieEntity);

        if (response.get("status").equals(true)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
