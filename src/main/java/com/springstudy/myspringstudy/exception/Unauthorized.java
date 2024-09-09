package com.springstudy.myspringstudy.exception;

import org.springframework.http.HttpStatus;

public class Unauthorized extends MySuperException {
    private static final String MESSAGE = "인증되지 않은 사용자";

    public Unauthorized() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }
    @Override
    public int getStatusCode() {
        return 401;
    }
}
