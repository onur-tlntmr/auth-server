package com.example.springjwt.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiException {

    private final String message;

    private final HttpStatus httpStatus;

    private String timestamp;

    public ApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        setTimestamp(ZonedDateTime.now(ZoneId.of("UTC+3")));
    }

    public void setTimestamp(ZonedDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss Z");
        this.timestamp = formatter.format(time);
    }
}
