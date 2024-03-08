package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.member.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class FindMatchingPostResInProfile {
    private Gender gender; // 성별
    private int studentNo; // 학번
    private String major; // 전공
    private Date birthdate; // 생년월일

    private Mbti mbti;
    private Smoking smoking;
    private List<SleepingHabit> sleepingHabit;
    private LifePattern lifePattern;
    private Outing outing;
    private CleaningCycle cleaningCycle;
    private Sensitivity soundSensitivity;

    public static FindMatchingPostResInProfile toFindMatchingPostResInProfile(Profile profile){
        return FindMatchingPostResInProfile.builder()
                .gender(profile.getGender())
                .studentNo(profile.getStudentNo())
                .major(profile.getMajor())
                .birthdate(profile.getBirthdate())
                .mbti(profile.getMbti())
                .smoking(profile.getSmoking())
                .sleepingHabit(profile.getSleepingHabit())
                .lifePattern(profile.getLifePattern())
                .outing(profile.getOuting())
                .cleaningCycle(profile.getCleaningCycle())
                .soundSensitivity(profile.getSoundSensitivity())
                .build();
    }
}
