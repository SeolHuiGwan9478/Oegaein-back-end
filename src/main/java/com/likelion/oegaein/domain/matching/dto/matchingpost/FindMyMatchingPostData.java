package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class FindMyMatchingPostData {
    private Long matchingPostId;
    private String title;
    private DongType dongType;
    private RoomSizeType roomSizeType;
    private LocalDate deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MatchingStatus matchingStatus;

    public static FindMyMatchingPostData toFindMyMatchingPostData(MatchingPost matchingPost) {
        return FindMyMatchingPostData.builder()
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .dongType(matchingPost.getDongType())
                .roomSizeType(matchingPost.getRoomSizeType())
                .deadline(matchingPost.getDeadline())
                .createdAt(matchingPost.getCreatedAt())
                .updatedAt(matchingPost.getUpdatedAt())
                .matchingStatus(matchingPost.getMatchingStatus())
                .build();
    }
}
