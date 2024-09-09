package com.springstudy.myspringstudy.exception.jwt;

import com.springstudy.myspringstudy.exception.MySuperException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotExist extends MySuperException {
    public static final String MESSAGE = "리프레시 토큰 조회에 실패했습니다";
    @Override
    public int getStatusCode() {
        return 1002;
    }
    public RefreshTokenNotExist() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
