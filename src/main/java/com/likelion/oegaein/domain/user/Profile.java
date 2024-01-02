package com.likelion.oegaein.domain.user;

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
    private int mbti;
    private int snoring;
    private int smoking;
    private LocalDate sleepTime;
    private int cleaningCycle;
    private int soundSensitivity;
    private String introduce;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
