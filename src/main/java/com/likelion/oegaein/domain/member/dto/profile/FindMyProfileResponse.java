package com.likelion.oegaein.domain.member.dto.profile;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.member.entity.profile.*;
import com.likelion.oegaein.global.dto.ResponseDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindMyProfileResponse implements ResponseDto {
    private Long memberId;
    private String photoUrl; // 프로필 사진 url
    private String name; // 닉네임
    private String introduction; // 소개글
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별
    private int studentNo; // 학번
    private Major major; // 전공
    private Date birthdate; // 생일
    @Enumerated(EnumType.STRING)
    private Mbti mbti; // MBTI
    private List<SleepingHabit> sleepingHabit; // 수면 습관
    @Enumerated(EnumType.STRING)
    private LifePattern lifePattern; // 생활 패턴
    @Enumerated(EnumType.STRING)
    private Smoking smoking; // 흡연 여부
    @Enumerated(EnumType.STRING)
    private CleaningCycle cleaningCycle; // 청소 주기
    @Enumerated(EnumType.STRING)
    private Outing outing; // 외출 빈도
    @Enumerated(EnumType.STRING)
    private Sensitivity soundSensitivity; // 소리 민감도

    public static FindMyProfileResponse of(Profile profile) {;
        List<SleepingHabit> sleepingHabit = profile.getSleepingHabit().stream()
                .map(SleepingHabitEntity::getSleepingHabit)
                .collect(Collectors.toList());

        return FindMyProfileResponse.builder()
                .memberId(profile.getMember().getId())
                .photoUrl(profile.getMember().getPhotoUrl())
                .name(profile.getName())
                .introduction(profile.getIntroduction())
                .gender(profile.getGender())
                .studentNo(profile.getStudentNo())
                .major(profile.getMajor())
                .birthdate(profile.getBirthdate())
                .mbti(profile.getMbti())
                .sleepingHabit(sleepingHabit)
                .lifePattern(profile.getLifePattern())
                .smoking(profile.getSmoking())
                .cleaningCycle(profile.getCleaningCycle())
                .outing(profile.getOuting())
                .soundSensitivity(profile.getSoundSensitivity())
                .build();
    }
}
