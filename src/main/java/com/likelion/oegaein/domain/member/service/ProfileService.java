package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.dto.member.CheckDuplicateNameResponse;
import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.entity.member.Likey;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabit;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabitEntity;
import com.likelion.oegaein.domain.member.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final SleepingHabitRepository sleepingHabitRepository;
    private final BlockRepository blockRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    public CreateProfileResponse createProfile(String email, CreateProfileRequest form) {
        Member loginMember = findAuthenticatedMember(email);
        
        // 닉네임 중복 확인
        isValidName(form.getName());
        
        // 내용 저장
        Profile profile = Profile.builder()
                .name(form.getName())
                .introduction(form.getIntroduction())
                .gender(form.getGender())
                .studentNo(form.getStudentNo())
                .major(form.getMajor())
                .birthdate(form.getBirthdate())
                .mbti(form.getMbti())
                .lifePattern(form.getLifePattern())
                .smoking(form.getSmoking())
                .cleaningCycle(form.getCleaningCycle())
                .outing(form.getOuting())
                .soundSensitivity(form.getSoundSensitivity())
                .build();
        profile.setMember(loginMember);
        profileRepository.save(profile);
        loginMember.updateProfileSetUpStatus();
        // 수면습관 설정
        updateSleepingHabit(form.getSleepingHabit(), profile);

        return new CreateProfileResponse(profile.getId());
    }

    public UpdateProfileResponse updateProfile(String email, UpdateProfileRequest form) {
        Member loginMember = findAuthenticatedMember(email);// 사용자 찾기
        if (!loginMember.getProfile().getName().equals(form.getName())) {
            isValidName(form.getName());
        }
        Profile profile = profileRepository.findById(loginMember.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Profile: " + loginMember.getId()));
        profile.set(form);
        updateSleepingHabit(form.getSleepingHabit(), profile);
        return new UpdateProfileResponse(profile.getId());
    }

    public void updateSleepingHabit(List<SleepingHabit> newSleepingHabits, Profile profile) {
        // 기존 내용 delete
        Optional<List<SleepingHabitEntity>> findSleepingHabitEntities = sleepingHabitRepository.findAllByProfile(profile);
        findSleepingHabitEntities.ifPresent(sleepingHabitEntities -> {
            for (SleepingHabitEntity sleepingHabitEntity : sleepingHabitEntities) {
                sleepingHabitRepository.delete(sleepingHabitEntity);
            }
        });

        // 새로운 내용 생성
        Optional.ofNullable(newSleepingHabits)
                .ifPresent(habits -> habits.forEach(habit -> {
                    SleepingHabitEntity sleepingHabitEntity = SleepingHabitEntity.builder()
                            .sleepingHabit(habit)
                            .profile(profile)
                            .build();
                    sleepingHabitRepository.save(sleepingHabitEntity);
                }));
    }

    public FindProfileResponse findProfile(Authentication authentication, Long memberId) {
        Member loginMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Boolean isLike = Boolean.FALSE;
        // 차단 확인
        isBlockedMember(loginMember, findMember);
        Profile profile = findMember.getProfile();
        Optional<Likey> likey = likeRepository.findBySenderAndReceiver(loginMember, findMember);
        if(likey.isPresent()) isLike = Boolean.TRUE;
        return FindProfileResponse.of(profile, isLike);
    }

    public FindMyProfileResponse findMyProfile(String email){
        Member loginMember = findAuthenticatedMember(email);
        Profile profile = loginMember.getProfile();
        return FindMyProfileResponse.of(profile);
    }

    // 유효 닉네임 검사
    public CheckDuplicateNameResponse isValidName(String nickname) {
        Optional<Profile> member = profileRepository.findByName(nickname);
        if (member.isPresent()) {
            return new CheckDuplicateNameResponse(true);
        }
        else {
            return new CheckDuplicateNameResponse(false);
        }
    }

    // 차단 확인
    public void isBlockedMember(Member loginMember, Member findMember) {
        if (blockRepository.isBlocked(loginMember.getId(), findMember.getId())) {
            throw new IllegalStateException("차단된 사용자입니다.");
        }
    }

    // 리뷰 평점 계산
    public void setAverageScore(Member member) {
        Profile profile = member.getProfile();
        double averageScore = reviewRepository.averageScoreByReceiver(member);
        profile.setScore(averageScore);
    }

    // 로그인한 사용자 찾기
    private Member findAuthenticatedMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + email));
    }

    // 전공 추출
//    private String extractMajor(String googleName) {
//        // 정규표현식으로 전공 추출
//        Pattern pattern = Pattern.compile("\\/\\s*(.*?)\\s*\\]");
//        Matcher matcher = pattern.matcher(googleName);
//
//        // 추출된 전공을 반환
//        if (matcher.find()) {
//            String extracted = matcher.group(1); // 첫 번째 그룹에 해당하는 문자열 추출
//            log.info("전공: " + extracted);
//            return extracted;
//        } else {
//            log.info("추출할 전공이 없습니다.");
//            return null;
//        }
//    }
}
