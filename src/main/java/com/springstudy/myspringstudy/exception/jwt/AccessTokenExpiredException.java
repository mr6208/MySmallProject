package com.springstudy.myspringstudy.exception.jwt;

import com.springstudy.myspringstudy.exception.MySuperException;
import org.springframework.http.HttpStatus;

public class AccessTokenExpiredException extends MySuperException {
    public static final String MESSAGE = "만료된 엑세스 토큰입니다.";
    @Override
    public int getStatusCode() {
        return 1003;
    }
    public AccessTokenExpiredException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
