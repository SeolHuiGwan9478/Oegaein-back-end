package com.likelion.oegaein.domain.matching;

import com.likelion.oegaein.domain.global.Gender;
import com.likelion.oegaein.domain.global.RoomSize;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DormType {
    @Id @GeneratedValue
    @Column(name = "dorm_type_id")
    private Long id;

    private char dong;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private RoomSize roomSize;
}