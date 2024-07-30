package com.likelion.oegaein.domain.member.dto.profile;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.member.entity.profile.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfileData {
    private String name;
    private Gender gender;
    private int studentNo;
    private Date birthdate;
    private Major major;
    private String introduction;
    private Mbti mbti;
    private List<SleepingHabit> sleepingHabit;
    private LifePattern lifePattern;
    private Smoking smoking;
    private CleaningCycle cleaningCycle;
    private Outing outing;
    private Sensitivity soundSensitivity;
}
