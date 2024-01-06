package com.likelion.oegaein.domain.member;

import com.likelion.oegaein.domain.global.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    private String name;
    private Gender gender;
    private String studentNo;
    private String major;
    private LocalDate birthdate;
    private Mbti mbti;
    private Smoking smoking;
    private SleepingHabit sleepingHabit;
//    private LocalDate sleepTime;
    private LifePattern lifePattern;
    private Outing outing;
    private CleaningCycle cleaningCycle;
    private Sensitivity soundSensitivity;
    private String introduce;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
