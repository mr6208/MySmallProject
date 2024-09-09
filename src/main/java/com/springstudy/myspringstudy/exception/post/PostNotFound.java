package com.springstudy.myspringstudy.exception.post;


import com.springstudy.myspringstudy.exception.MySuperException;
import org.springframework.http.HttpStatus;

/**
 * 상태코드 -> 404
 */
public class PostNotFound extends MySuperException {

    private static final String MESSAGE = "존재하지 않는 글 입니다.";

    public PostNotFound() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
