package com.example.JWT.Authentication.Project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
