package com.likelion.oegaein.service.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.domain.member.Profile;
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
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    public void update(String email, SetProfileDto form) {
        // 사용자 찾기
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Member is not present"));
        Profile profile = profileRepository.findByMember(member);

        // 내용 저장
        Profile newProfile = Profile.builder()
                .name(form.getName())
                .gender(form.getGender())
                .studentNo(form.getStudentNo())
                .major(form.getMajor())
                .birthdate(form.getBirthdate())
                .dormitory(form.getDormitory())
                .mbti(form.getMbti())
                .sleepingHabit(form.getSleepingHabit())
                .lifePattern(form.getLifePattern())
                .smoking(form.getSmoking())
                .cleaningCycle(form.getCleaningCycle())
                .outing(form.getOuting())
                .soundSensitivity(form.getSoundSensitivity())
                .build();
        profileRepository.save(profile);
    }
}
