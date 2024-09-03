package com.example.JWT.Authentication.Project.services;

import com.example.JWT.Authentication.Project.entities.MovieEntity;
import com.example.JWT.Authentication.Project.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MovieServices {

    @Autowired
    private MovieRepository movieRepository;

    public Map<String, Object> saveMovie(MovieEntity movieEntity) {

        Map<String, Object> response = new HashMap<>();

        try {
            movieRepository.save(movieEntity);
            response.put("status", true);
            response.put("description", "Created successfully");
            return response;
        } catch (Exception e) {
            response.put("status", false);
            return response;
        }

    }

}
