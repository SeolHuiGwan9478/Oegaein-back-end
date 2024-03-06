package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindMatchingPostsData {
    private String title; // Title
    private Long restDay; // D-day
    private DongType dong;
    private RoomSizeType roomSize;
    private MatchingStatus matchingStatus;
    // private Long progressRate // 진행률
    // private Member author; // author information
}
