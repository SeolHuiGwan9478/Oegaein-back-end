package com.likelion.oegaein.service.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    public void join(String email, String refreshToken) {
        validateDuplicateEmail(email);
        Member member = Member.builder()
                .email(email)
                .refreshToken(refreshToken)
                .build();
        memberRepository.save(member);
    }

    // 중복 회원 검사
    private void validateDuplicateEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 유효 닉네임 검사
    private void isValidName(String name) {
        Optional<Member> findMember = memberRepository.findByName(name);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
}
