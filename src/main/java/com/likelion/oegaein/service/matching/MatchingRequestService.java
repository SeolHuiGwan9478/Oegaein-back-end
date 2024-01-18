package com.likelion.oegaein.service.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.domain.matching.MatchingRequest;
import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.matching.CreateMatchingReqData;
import com.likelion.oegaein.dto.matching.CreateMatchingReqResponse;
import com.likelion.oegaein.dto.matching.FindMatchingReqData;
import com.likelion.oegaein.dto.matching.FindMatchingReqsResponse;
import com.likelion.oegaein.repository.matching.MatchingPostRepository;
import com.likelion.oegaein.repository.matching.MatchingRequestRepository;
import com.likelion.oegaein.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchingRequestService {
    private final MatchingRequestRepository matchingRequestRepository;
    private final MatchingPostRepository matchingPostRepository;
    private final MemberRepository memberRepository;

    private final int PERCENTAGE_VALUE = 100;

    public FindMatchingReqsResponse findByParticipantMatchingRequest(Long participantId){
        Member participant = memberRepository.findById(participantId);
        List<MatchingRequest> matchingRequests = matchingRequestRepository.findByParticipant(participant);
        List<FindMatchingReqData> matchingReqsData = new ArrayList<>();
        for(MatchingRequest matchingRequest : matchingRequests){
            MatchingPost matchingPost = matchingPostRepository.findById(matchingRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Reference Error: " + matchingRequest.getId()));
            // calculate matching rate
            double numOfTargetMatchingRequest = matchingPost.getRoomSize().getValueNum();
            double numOfMatchingRequest = matchingPost.getMatchingRequests().size();
            double matchingRate = (numOfMatchingRequest/numOfTargetMatchingRequest) * PERCENTAGE_VALUE;
            // create FindMatchingReqData
            FindMatchingReqData matchingReqData = new FindMatchingReqData(matchingRate, matchingPost.getTitle());
            matchingReqsData.add(matchingReqData);
        }
        return new FindMatchingReqsResponse(matchingReqsData.size(), matchingReqsData);
    }

    @Transactional
    public CreateMatchingReqResponse createMatchingRequest(CreateMatchingReqData dto){
        // find matchingPost
        MatchingPost findMatchingPost = matchingPostRepository.findById(dto.getMatchingPostId())
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + dto.getMatchingPostId()));
        // find participant
        Member findParticipant = new Member(); // 임시 생성자
        MatchingRequest newMatchingRequest = new MatchingRequest(findMatchingPost, findParticipant);
        matchingRequestRepository.save(newMatchingRequest);
        return new CreateMatchingReqResponse(newMatchingRequest.getId());
    }

    @Transactional
    public void removeMatchingRequest(Long matchingRequestId){
        MatchingRequest matchingRequest = matchingRequestRepository.findById(matchingRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingRequestId));
        matchingRequestRepository.delete(matchingRequest);
    }
}
