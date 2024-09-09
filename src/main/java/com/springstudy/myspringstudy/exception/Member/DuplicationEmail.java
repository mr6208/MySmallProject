package com.springstudy.myspringstudy.exception.Member;

import com.springstudy.myspringstudy.exception.MySuperException;
import org.springframework.http.HttpStatus;

public class DuplicationEmail extends MySuperException {
    public static final String MESSAGE = "중복된 이메일입니다.";
    @Override
    public int getStatusCode() {
        return 400;
    }
    public DuplicationEmail() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
