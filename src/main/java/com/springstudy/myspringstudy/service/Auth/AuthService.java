package com.springstudy.myspringstudy.service.Auth;

import com.springstudy.myspringstudy.config.jwt.JWTUtil;
import com.springstudy.myspringstudy.domain.Member.Member;
import com.springstudy.myspringstudy.dto.Member.request.JoinRequest;
import com.springstudy.myspringstudy.exception.Member.DuplicationEmail;
import com.springstudy.myspringstudy.exception.jwt.RefreshTokenExpiredException;
import com.springstudy.myspringstudy.exception.jwt.RefreshTokenNotExist;
import com.springstudy.myspringstudy.exception.jwt.TokenNotValidateException;
import com.springstudy.myspringstudy.repository.Member.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(JoinRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicationEmail();
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);

        return member.getId();
    }

    public String reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            throw new RefreshTokenNotExist();
        }

        // 만료여부 검사
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException();
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            throw new TokenNotValidateException();
        }

        // TODO redis 내부에 토큰이 있는지 검증 로직

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        String accessToken = jwtUtil.createJwt("access", username, role);
        String refreshToken = jwtUtil.createJwt("refresh", username, role);

        response.addCookie(createCookie("refresh", refreshToken));

        return accessToken;
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setSecure(true); HTTPS 통신시 주석 해제
//        cookie.setPath("/"); 쿠키가 적용 될 범위 설정 필요시 사용
        cookie.setHttpOnly(true);

        return cookie;
    }
}
