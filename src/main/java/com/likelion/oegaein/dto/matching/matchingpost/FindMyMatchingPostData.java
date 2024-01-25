package com.likelion.oegaein.dto.matching.matchingpost;

import com.likelion.oegaein.domain.matching.*;
import com.likelion.oegaein.domain.member.Profile;
import com.likelion.oegaein.dto.matching.matchingrequest.FindMatchingReqInPostData;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FindMyMatchingPostData {
    private String title;
    private DongType dong;
    private RoomSizeType roomSize;
    private LocalDateTime createdAt;
    private MatchingStatus matchingStatus; // 매칭 상태
    private List<FindMatchingReqInPostData> matchingRequests; // 매칭 요청 현황

    // convert to FindMyMatchingPostData
    public static FindMyMatchingPostData toFindMyMatchingPostData(MatchingPost matchingPost){
        FindMyMatchingPostData findMyMatchingPostData = new FindMyMatchingPostData();
        findMyMatchingPostData.setTitle(matchingPost.getTitle());
        findMyMatchingPostData.setDong(matchingPost.getDong());
        findMyMatchingPostData.setRoomSize(matchingPost.getRoomSize());
        findMyMatchingPostData.setMatchingStatus(matchingPost.getMatchingStatus());
        // 매칭 참가자 현황
        List<MatchingRequest> findMatchingRequests = matchingPost.getMatchingRequests();
        List<FindMatchingReqInPostData> convertedMatchingRequests = findMatchingRequests.stream().map(
                findMatchingRequest -> FindMatchingReqInPostData.toFindMatchingReqInPostData(
                        findMatchingRequest.getParticipant().getProfile()) // 임시 프로필 대입
        ).toList();
        findMyMatchingPostData.setMatchingRequests(convertedMatchingRequests);
        return findMyMatchingPostData;
    }
}
