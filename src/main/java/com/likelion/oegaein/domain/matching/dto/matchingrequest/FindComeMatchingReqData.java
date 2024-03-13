package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindComeMatchingReqData {
    private Long matchingRequestId;
    private Long matchingPostId;
    private String matchingPostTitle;
    private String name;
    private String major;
    private int studentNo;

    public static FindComeMatchingReqData toFindComeMatchingReqData(MatchingRequest matchingRequest){
        Member participant = matchingRequest.getParticipant();
        Profile profile = participant.getProfile();
        MatchingPost matchingPost = matchingRequest.getMatchingPost();
        return FindComeMatchingReqData.builder()
                .matchingRequestId(matchingRequest.getId())
                .matchingPostId(matchingPost.getId())
                .matchingPostTitle(matchingPost.getTitle())
                .name(profile.getName())
                .major(profile.getMajor())
                .studentNo(profile.getStudentNo())
                .build();
    }
}
