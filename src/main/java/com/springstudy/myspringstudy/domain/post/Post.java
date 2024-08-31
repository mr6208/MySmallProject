package com.springstudy.myspringstudy.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String content;
    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String content, String title) {
        this.content = content;
        this.title = title;
    }
}


