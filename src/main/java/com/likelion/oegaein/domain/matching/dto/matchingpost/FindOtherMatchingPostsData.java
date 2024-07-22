package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.domain.member.entity.profile.Major;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
public class FindOtherMatchingPostsData {
    private int star; // rate
    private Major major; // major
    private int studentNo; // studentNo
    private String name; // name
    private int targetOfNumber; // targetOfNumber
    private String photoUrl; // photoUrl
    private Long matchingPostId; // matchingPost ID
    private String title; // Title
    private Long dDay; // D-day
    private DongType dong; // dorm dong
    private RoomSizeType roomSize; // dorm roomSize
    private MatchingStatus matchingStatus; // matching status
    private LocalDateTime createdAt;

    public static FindOtherMatchingPostsData toFindOtherMatchingPostsData(MatchingPost matchingPost){
        Profile profile = matchingPost.getAuthor().getProfile();
        return FindOtherMatchingPostsData.builder()
                .major(profile.getMajor())
                .studentNo(profile.getStudentNo())
                .name(profile.getName())
                .targetOfNumber(matchingPost.getTargetNumberOfPeople())
                .photoUrl(matchingPost.getAuthor().getPhotoUrl())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .dDay(ChronoUnit.DAYS.between(LocalDate.now(), matchingPost.getDeadline()))
                .dong(matchingPost.getDongType())
                .roomSize(matchingPost.getRoomSizeType())
                .matchingStatus(matchingPost.getMatchingStatus())
                .createdAt(matchingPost.getCreatedAt())
                .build();
    }
}
