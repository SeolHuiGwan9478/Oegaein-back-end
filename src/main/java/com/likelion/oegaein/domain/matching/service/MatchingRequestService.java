package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.matchingrequest.*;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.matching.repository.query.MatchingRequestQueryRepository;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.MatchingRequestRepository;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchingRequestService {
    private final MatchingRequestRepository matchingRequestRepository;
    private final MatchingRequestQueryRepository matchingRequestQueryRepository;
    private final MatchingPostRepository matchingPostRepository;
    private final MemberRepository memberRepository;

    public FindMyMatchingReqsResponse findMyMatchingRequest(){
        Member participant = memberRepository.findById(1L) // 인증 유저 조회
                .orElseThrow(() -> new IllegalArgumentException("Not Found: Participant"));
        // 내 매칭 요청 목록 조회
        List<MatchingRequest> matchingRequests = matchingRequestRepository.findByParticipant(participant);
        List<FindMyMatchingReqData> matchingReqDatas = matchingRequests.stream()
                .map(FindMyMatchingReqData::toFindMatchingReqData)
                .toList();
        return new FindMyMatchingReqsResponse(matchingReqDatas.size(), matchingReqDatas);
    }

    public FindComeMatchingReqsResponse findComeMatchingRequest(){
        Member author = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: Author")); // 인증 유저 조회
        List<MatchingRequest> matchingRequests = matchingRequestQueryRepository.findComeMatchingRequests(author);
        List<FindComeMatchingReqData> matchingReqDatas = matchingRequests.stream()
                .map(FindComeMatchingReqData::toFindComeMatchingReqData)
                .toList();
        return new FindComeMatchingReqsResponse(matchingReqDatas.size(), matchingReqDatas);
    }

    @Transactional
    public CreateMatchingReqResponse createMatchingRequest(CreateMatchingReqData dto){
        // find matchingPost
        MatchingPost findMatchingPost = matchingPostRepository.findById(dto.getMatchingPostId())
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + dto.getMatchingPostId()));
        // find participant
        Member findParticipant = new Member(); // 임시 로그인 유저
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

    @Transactional
    public AcceptMatchingReqResponse acceptMatchingRequest(Long matchingRequestId){
        MatchingRequest matchingRequest = matchingRequestRepository.findById(matchingRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingRequestId));
        // change matchingAcceptance
        matchingRequest.acceptMatchingRequest();
        // return matchingReqResponse
        return new AcceptMatchingReqResponse(
                matchingRequest.getId(),
                1L // 임시 채팅방 번호
        );
    }

    @Transactional
    public RejectMatchingReqResponse rejectMatchingRequest(Long matchingRequestId){
        MatchingRequest matchingRequest = matchingRequestRepository.findById(matchingRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingRequestId));
        // change matchingAcceptance
        matchingRequest.rejectMatchingRequest();
        // return matchingReqResponse
        return new RejectMatchingReqResponse(
                matchingRequest.getId()
        );
    }
}
