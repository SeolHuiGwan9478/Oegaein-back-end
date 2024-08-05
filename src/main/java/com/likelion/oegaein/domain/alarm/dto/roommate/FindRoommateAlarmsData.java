package com.likelion.oegaein.domain.alarm.dto.roommate;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.alarm.entity.RoommateAlarmType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindRoommateAlarmsData {
    private Long roommateAlarmId; // 알림 ID
    private String name; // 이름
    private String photoUrl; // 프로필 사진
    private RoommateAlarmType roommateAlarmType; // 알림 타입
    private Long matchingPostId; // 매칭글 ID
    private String title; // 매칭글 제목
    private LocalDateTime createdAt; // 생성일

    public static FindRoommateAlarmsData toFindRoommateAlarmsData(RoommateAlarm roommateAlarm){
        Member member;
        if(roommateAlarm.getAlarmType().equals(RoommateAlarmType.MATCHING_REQUEST)){
            member = roommateAlarm.getMatchingRequest().getParticipant();
        }else{
            member = roommateAlarm.getMatchingPost().getAuthor();
        }
        Profile profile = member.getProfile();
        MatchingPost matchingPost = roommateAlarm.getMatchingPost();
        return FindRoommateAlarmsData.builder()
                .roommateAlarmId(roommateAlarm.getId())
                .name(profile.getName())
                .photoUrl(member.getPhotoUrl())
                .roommateAlarmType(roommateAlarm.getAlarmType())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .createdAt(roommateAlarm.getCreatedAt())
                .build();
    }
}