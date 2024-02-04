package com.likelion.oegaein.dto.member;

import com.likelion.oegaein.domain.member.*;
import lombok.Data;

import java.util.Date;

@Data
public class ProfileSettingDto {
    private String name;
    private Gender gender;
    private int studentNo;
    private String major;
    private Date birthdate;
    private Dormitory dormitory;
    private Mbti mbti;
    private SleepingHabit sleepingHabit;
    private LifePattern lifePattern;
    private Smoking smoking;
    private CleaningCycle cleaningCycle;
    private Outing outing;
    private Sensitivity soundSensitivity;
}
