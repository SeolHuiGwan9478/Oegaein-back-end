package com.likelion.oegaein.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.oegaein.domain.member.*;
import lombok.Data;

import java.util.Date;

@Data
public class SetProfileDto {
    private String name;
    private Gender gender;
    @JsonProperty("student_no")
    private int studentNo;
    private String major;
    private Date birthdate;
    private Dormitory dormitory;
    private Mbti mbti;
    @JsonProperty("sleeping_habit")
    private SleepingHabit sleepingHabit;
    @JsonProperty("lifePattern")
    private LifePattern lifePattern;
    private Smoking smoking;
    @JsonProperty("cleaning_cycle")
    private CleaningCycle cleaningCycle;
    private Outing outing;
    @JsonProperty("sound_sensitivity")
    private Sensitivity soundSensitivity;
}
