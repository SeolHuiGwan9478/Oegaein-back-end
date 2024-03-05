package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.Profile;
import com.likelion.oegaein.domain.auth.dto.GoogleInfoDto;
import com.likelion.oegaein.domain.auth.dto.GoogleResponseDto;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    // 회원 가입
    public void join(GoogleInfoDto googleInfo, GoogleResponseDto jwtToken) {
        Profile profile = new Profile();
        profileRepository.save(profile);

        Member member = Member.builder()
                .email(googleInfo.getEmail())
                .refreshToken(jwtToken.getRefreshToken())
                .profile(profile)
                .build();
        memberRepository.save(member);
    }

    // 이메일로 사용자 반환
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
