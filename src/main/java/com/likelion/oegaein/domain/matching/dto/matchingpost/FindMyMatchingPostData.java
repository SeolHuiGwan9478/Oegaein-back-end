package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.profile.Gender;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class FindMyMatchingPostData {
    private String photoUrl;
    private String name;
    private Gender gender;
    private int targetNumberOfPeople;
    private Long matchingPostId;
    private String title;
    private DongType dong;
    private RoomSizeType roomSize;
    private LocalDate deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MatchingStatus matchingStatus;

    public static FindMyMatchingPostData toFindMyMatchingPostData(MatchingPost matchingPost) {
        Member author = matchingPost.getAuthor();
        Profile profile = author.getProfile();
        return FindMyMatchingPostData.builder()
                .photoUrl(author.getPhotoUrl())
                .name(profile.getName())
                .gender(profile.getGender())
                .targetNumberOfPeople(matchingPost.getTargetNumberOfPeople())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .dong(matchingPost.getDongType())
                .roomSize(matchingPost.getRoomSizeType())
                .deadline(matchingPost.getDeadline())
                .createdAt(matchingPost.getCreatedAt())
                .updatedAt(matchingPost.getUpdatedAt())
                .matchingStatus(matchingPost.getMatchingStatus())
                .build();
    }
}
