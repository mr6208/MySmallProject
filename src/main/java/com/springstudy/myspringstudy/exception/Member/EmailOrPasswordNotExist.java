package com.springstudy.myspringstudy.exception.Member;

import com.springstudy.myspringstudy.exception.MySuperException;

public class EmailOrPasswordNotExist extends MySuperException {
    public static final String MESSAGE = "데이터베이스 내부에 존재하지 않는 이메일 혹은 비밀번호입니다.";
    @Override
    public int getStatusCode() {
        return 401;
    }
    public EmailOrPasswordNotExist() {
        super(MESSAGE);
    }
}
