package com.likelion.oegaein.service.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.domain.member.Profile;
import com.likelion.oegaein.dto.member.UpdateProfileDto;
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

    public void update(String email, UpdateProfileDto form) {
        // 사용자 찾기
        Member member = memberRepository.findByEmail(email);

        // 닉네임 중복 확인
        isValidName(form.getName());

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
        profileRepository.save(newProfile);
    }

    // 유효 닉네임 검사
    private void isValidName(String name) {
        Optional<Profile> member = profileRepository.findByName(name);
        if (member.isPresent()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
}
