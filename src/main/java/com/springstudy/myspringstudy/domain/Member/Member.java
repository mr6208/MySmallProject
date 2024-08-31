package com.springstudy.myspringstudy.domain.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime createdAt;
    private String role;

    @Builder
    public Member(String email, String password, String name, LocalDateTime createdAt, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.role = role;
    }
}
