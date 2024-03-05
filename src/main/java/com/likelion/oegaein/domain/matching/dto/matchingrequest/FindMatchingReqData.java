package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.domain.matching.entity.MatchingAcceptance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchingReqData {
    private Long matchingPostId; // 매칭글 ID
    private String matchingPostTitle; // 제목
    private double matchingRate; // 매칭률
    private MatchingAcceptance matchingAcceptance; // 매칭 상태
}
