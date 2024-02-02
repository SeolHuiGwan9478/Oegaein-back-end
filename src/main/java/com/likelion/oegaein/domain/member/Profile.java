package com.likelion.oegaein.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    private Gender gender;
    private String studentNo;
    private String major;
    private LocalDate birthdate;
    private Dormitory dormitory;
    private Mbti mbti;
    private Smoking smoking;
    private SleepingHabit sleepingHabit;
    private LifePattern lifePattern;
    private Outing outing;
    private CleaningCycle cleaningCycle;
    private Sensitivity soundSensitivity;
    private String introduce;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;
}
