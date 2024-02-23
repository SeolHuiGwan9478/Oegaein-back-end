package com.likelion.oegaein.service.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.domain.member.Profile;
import com.likelion.oegaein.dto.auth.GoogleInfoDto;
import com.likelion.oegaein.dto.auth.GoogleResponseDto;
import com.likelion.oegaein.dto.member.SetProfileDto;
import com.likelion.oegaein.repository.member.MemberRepository;
import com.likelion.oegaein.repository.member.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    // 회원 가입
    public void join(GoogleInfoDto googleInfo, GoogleResponseDto jwtToken) {
        Member member = Member.builder()
                .email(googleInfo.getEmail())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
        memberRepository.save(member);
    }

    // 유효 닉네임 검사
    private void isValidName(String name) {
        Optional<Member> findMember = profileRepository.findByName(name);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    // 이메일로 사용자 반환
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
