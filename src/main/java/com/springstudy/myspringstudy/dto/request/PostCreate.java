package com.springstudy.myspringstudy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@ToString
public class PostCreate {
    @NotBlank(message = "제목을 입력해주세용")
    private String title;
    @NotBlank(message = "내용을 입력해주세용")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
