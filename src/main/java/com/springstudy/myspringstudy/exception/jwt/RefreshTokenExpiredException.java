package com.springstudy.myspringstudy.exception.jwt;

import com.springstudy.myspringstudy.exception.MySuperException;
import org.springframework.http.HttpStatus;

public class RefreshTokenExpiredException extends MySuperException {
    public static final String MESSAGE = "만료된 리프레시 토큰입니다.";
    @Override
    public int getStatusCode() {
        return 1000;
    }
    public RefreshTokenExpiredException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
