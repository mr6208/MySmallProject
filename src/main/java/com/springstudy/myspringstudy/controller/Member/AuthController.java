package com.springstudy.myspringstudy.controller.Member;


import com.springstudy.myspringstudy.dto.Member.request.JoinRequest;
import com.springstudy.myspringstudy.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody @Valid JoinRequest request) {
        return ResponseEntity.ok(memberService.join(request));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return ResponseEntity.ok("테스트입니당   " + email + "  " + role);
    }
}
