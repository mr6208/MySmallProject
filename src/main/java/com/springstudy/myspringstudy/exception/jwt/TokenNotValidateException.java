package com.springstudy.myspringstudy.exception.jwt;

import com.springstudy.myspringstudy.exception.MySuperException;
import org.springframework.http.HttpStatus;

public class TokenNotValidateException extends MySuperException {
    public static final String MESSAGE = "잘못된 JWT 토큰입니다.";
    @Override
    public int getStatusCode() {
        return 1001;
    }
    public TokenNotValidateException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
