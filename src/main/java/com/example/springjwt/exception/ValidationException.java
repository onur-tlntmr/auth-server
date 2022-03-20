package com.example.springjwt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class ValidationException extends ApiException {

    @Getter
    private final Map<Object, Object> errors;

    public ValidationException(String message, Map<Object, Object> errors) {
        super(message, HttpStatus.BAD_REQUEST);
        this.errors = errors;
    }
}
