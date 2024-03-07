package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.domain.member.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
public class FindMatchingPostsData {
    private int roomMateRate; // rate
    private String major; // major
    private String studentNo; // studentNo
    private String name; // name
    private String title; // Title
    private Long dDay; // D-day
    private DongType dong; // dorm dong
    private RoomSizeType roomSize; // dorm roomSize
    private MatchingStatus matchingStatus; // matching status

    public static FindMatchingPostsData toFindMatchingPostsData(MatchingPost matchingPost){
        Profile profile = matchingPost.getAuthor().getProfile();
        return FindMatchingPostsData.builder()
                .major(profile.getMajor())
                //.studentNo(profile.getStudentNo())
                .name(profile.getName())
                .title(matchingPost.getTitle())
                .dDay(Duration.between(LocalDateTime.now(), matchingPost.getDeadline()).toDays())
                .dong(matchingPost.getDong())
                .roomSize(matchingPost.getRoomSize())
                .matchingStatus(matchingPost.getMatchingStatus())
                .build();
    }
}
