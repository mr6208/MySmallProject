package com.springstudy.myspringstudy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 상태코드 -> 400
 */
@Getter
public class InvalidRequest extends MySuperException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
        addValidation(fieldName,message);
    }
    @Override
    public int getStatusCode() {
        return 400;
    }
}
