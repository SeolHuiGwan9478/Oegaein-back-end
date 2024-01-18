package com.likelion.oegaein.service.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.domain.matching.MatchingRequest;
import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.matching.CreateMatchingReqData;
import com.likelion.oegaein.dto.matching.CreateMatchingReqResponse;
import com.likelion.oegaein.repository.matching.MatchingPostRepository;
import com.likelion.oegaein.repository.matching.MatchingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchingRequestService {
    private final MatchingRequestRepository matchingRequestRepository;
    private final MatchingPostRepository matchingPostRepository;

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
