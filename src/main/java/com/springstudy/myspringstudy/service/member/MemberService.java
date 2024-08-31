package com.springstudy.myspringstudy.service.member;

import com.springstudy.myspringstudy.domain.Member.Member;
import com.springstudy.myspringstudy.dto.Member.request.JoinRequest;
import com.springstudy.myspringstudy.exception.Member.DuplicationEmail;
import com.springstudy.myspringstudy.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

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
}
