package com.springstudy.myspringstudy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class MySuperException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();
    private final HttpStatus httpStatus;

    public MySuperException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
