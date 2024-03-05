package com.likelion.oegaein.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.oegaein.domain.member.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdateProfileDto {
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonProperty("student_no")
    private int studentNo;
    private String major;
    private Date birthdate;
    @Enumerated(EnumType.STRING)
    private Dormitory dormitory;
    private String introduction;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @JsonProperty("sleeping_habit")
    private List<SleepingHabit> sleepingHabit;
    @Enumerated(EnumType.STRING)
    @JsonProperty("lifePattern")
    private LifePattern lifePattern;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    @Enumerated(EnumType.STRING)
    @JsonProperty("cleaning_cycle")
    private CleaningCycle cleaningCycle;
    @Enumerated(EnumType.STRING)
    private Outing outing;
    @Enumerated(EnumType.STRING)
    @JsonProperty("sound_sensitivity")
    private Sensitivity soundSensitivity;
}
