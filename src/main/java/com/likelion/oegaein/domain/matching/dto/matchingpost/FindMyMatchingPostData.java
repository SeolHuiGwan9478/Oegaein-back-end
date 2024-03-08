package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindMyMatchingPostData {
    private Long matchingPostId;

    public static FindMyMatchingPostData toFindMyMatchingPostData(MatchingPost matchingPost) {
        return FindMyMatchingPostData.builder()
                .matchingPostId(matchingPost.getId())
                .build();
    }
}
