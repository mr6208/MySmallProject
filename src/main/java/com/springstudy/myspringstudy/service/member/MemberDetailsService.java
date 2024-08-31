package com.springstudy.myspringstudy.service.member;

import com.springstudy.myspringstudy.domain.Member.Member;
import com.springstudy.myspringstudy.dto.Member.request.MemberDetails;
import com.springstudy.myspringstudy.exception.Member.EmailOrPasswordNotExist;
import com.springstudy.myspringstudy.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(EmailOrPasswordNotExist::new);

        return new MemberDetails(member.getEmail(),member.getPassword(),member.getRole());
    }
}
