package com.likelion.oegaein.domain.member.entity.profile;

import com.likelion.oegaein.domain.member.dto.profile.UpdateProfileRequest;
import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int studentNo;
    @Enumerated(EnumType.STRING)
    private Major major;
    private Date birthdate;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    @OneToMany(mappedBy = "profile", orphanRemoval = true)
    private List<SleepingHabitEntity> sleepingHabit;
    @Enumerated(EnumType.STRING)
    private LifePattern lifePattern;
    @Enumerated(EnumType.STRING)
    private Outing outing;
    @Enumerated(EnumType.STRING)
    private CleaningCycle cleaningCycle;
    @Enumerated(EnumType.STRING)
    private Sensitivity soundSensitivity;
    @Size(max = 20)
    private String introduction = "";
    @Setter
    private double score;
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void set(UpdateProfileRequest request) {
        this.name = request.getName();
        this.gender = request.getGender();
        this.studentNo = request.getStudentNo();
        this.major = request.getMajor();
        this.birthdate = request.getBirthdate();
        this.mbti = request.getMbti();
        this.smoking = request.getSmoking();
        this.lifePattern = request.getLifePattern();
        this.outing = request.getOuting();
        this.cleaningCycle = request.getCleaningCycle();
        this.soundSensitivity = request.getSoundSensitivity();
        this.introduction = request.getIntroduction();
    }

    public void updateScore(int plusScore){
        this.score += plusScore;
    }
}
