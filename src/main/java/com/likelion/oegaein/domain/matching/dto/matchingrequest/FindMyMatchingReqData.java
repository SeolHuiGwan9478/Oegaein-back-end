package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.domain.matching.entity.MatchingAcceptance;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindMyMatchingReqData {
    private Long matchingRequestId; // 매칭 요청 ID
    private Long matchingPostId; // 매칭글 ID
    private String matchingPostTitle; // 제목
    private MatchingAcceptance matchingAcceptance; // 매칭 상태

    public static FindMyMatchingReqData toFindMatchingReqData(MatchingRequest matchingRequest){
        MatchingPost matchingPost = matchingRequest.getMatchingPost();
        return FindMyMatchingReqData.builder()
                .matchingRequestId(matchingRequest.getId())
                .matchingPostId(matchingPost.getId())
                .matchingPostTitle(matchingPost.getTitle())
                .matchingAcceptance(matchingRequest.getMatchingAcceptance())
                .build();
    }
}
