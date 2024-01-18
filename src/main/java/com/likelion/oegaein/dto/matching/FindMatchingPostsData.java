package com.likelion.oegaein.dto.matching;

import com.likelion.oegaein.domain.matching.DongType;
import com.likelion.oegaein.domain.matching.MatchingStatus;
import com.likelion.oegaein.domain.matching.RoomSizeType;
import com.likelion.oegaein.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
