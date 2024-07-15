package com.springstudy.myspringstudy.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdate {
    @NotBlank(message = "제목을 입력해주세용")
    private String title;
    @NotBlank(message = "내용을 입력해주세용")
    private String content;

    @Builder
    public PostUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
