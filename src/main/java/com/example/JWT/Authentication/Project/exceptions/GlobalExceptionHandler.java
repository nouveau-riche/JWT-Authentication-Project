package com.example.JWT.Authentication.Project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorCode = ((FieldError) error).getCode();

            if (Objects.equals(errorCode, "NotBlank")) {
                errors.put("code", "REQUIRED_FIELD_MISSING");
            } else if (Objects.equals(errorCode, "Email")) {
                errors.put("code", "INVALID_EMAIL_FORMAT");
            } else {
                errors.put("code", "SOME_ERROR");
            }

            String errorMessage = error.getDefaultMessage();

            errors.put("field", fieldName);
            errors.put("description", errorMessage);
            errors.put("status", false);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        Map<String, Object> response = new HashMap<>();

        String description;
        String[] parts = ex.getLocalizedMessage().split(":");

        if (parts.length != 0) {
            description = parts[0];
        } else {
            description = "Unable to read http message";
        }

        response.put("status", false);
        response.put("description", description);

        return ResponseEntity.badRequest().body(response);
    }

}
