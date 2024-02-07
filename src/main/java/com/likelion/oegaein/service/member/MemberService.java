package com.likelion.oegaein.service.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.domain.member.Profile;
import com.likelion.oegaein.dto.member.ProfileSettingDto;
import com.likelion.oegaein.repository.member.MemberRepository;
import com.likelion.oegaein.repository.member.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

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

    // 이메일로 사용자 반환
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 사용자 권한 검증
    public void verifyAuthorizedMember(Long memberId, String token) {
    }

    // 프로필 저장
    public void updateProfile(Member member, ProfileSettingDto profileDto) {
        Profile profile = Profile.builder()
                .build();
        profileRepository.save(profile);
    }
}
