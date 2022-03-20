package com.example.springjwt.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex,
             HttpHeaders headers, HttpStatus status,
             WebRequest request) {


        HashMap<Object, Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors()
                .forEach(element -> {
                    String fieldName = ((FieldError) element).getField();
                    String message = element.getDefaultMessage();
                    errors.put(fieldName, message);
                });


        ValidationException validationException = new ValidationException("Validation Error", errors);

        return new ResponseEntity<>(validationException, validationException.getHttpStatus());
    }
}
