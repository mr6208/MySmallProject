package com.springstudy.myspringstudy.dto.request;

import com.springstudy.myspringstudy.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreate {
//    @Size(min = 10, message = "제목은 최소 10자 이상입니다")
    @NotBlank(message = "제목을 입력해주세용")
    private String title;
//    @Size(min = 10,max = 500, message = "내용은 최소 10자 에서 500자 입니다")
    @NotBlank(message = "내용을 입력해주세용")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (title.contains("나쁜말")) {
            throw new InvalidRequest("title", "제목에 '나쁜말'은 안돼요");
        }
    }
}
