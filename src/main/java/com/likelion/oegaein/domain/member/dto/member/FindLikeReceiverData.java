package com.likelion.oegaein.domain.member.dto.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.member.entity.profile.Gender;
import com.likelion.oegaein.domain.member.entity.profile.Major;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindLikeReceiverData implements ResponseDto {
    private Long receiverId;
    private String name;
    private String photoUrl;
    private Gender gender;
    private Date birthdate;
    private int studentNo;
    private Major major;

    public static FindLikeReceiverData of(Profile profile) {
        return FindLikeReceiverData.builder()
                .receiverId(profile.getMember().getId())
                .name(profile.getName())
                .photoUrl(profile.getMember().getPhotoUrl())
                .gender(profile.getGender())
                .birthdate(profile.getBirthdate())
                .studentNo(profile.getStudentNo())
                .major(profile.getMajor())
                .build();
    }
}
