package com.likelion.oegaein.service.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.domain.matching.MatchingStatus;
import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.matching.*;
import com.likelion.oegaein.repository.matching.MatchingPostRepository;
import com.likelion.oegaein.repository.matching.query.MatchingPostQueryRepository;
import com.likelion.oegaein.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingPostService {
    private final MatchingPostRepository matchingPostRepository;
    private final MatchingPostQueryRepository matchingPostQueryRepository;
    private final MemberRepository memberRepository;

    // 모든 매칭글 조회
    public FindMatchingPostsResponse findAllMatchingPosts(){
        // find matchingPosts
        List<MatchingPost> matchingPosts = matchingPostRepository.findAll();
        // create matchingPostsData
        List<FindMatchingPostsData> matchingPostsData = matchingPosts.stream()
                .map(matchingPost -> FindMatchingPostsData.builder()
                        .title(matchingPost.getTitle())
                        .restDay(
                                Duration.between(LocalDateTime.now(), matchingPost.getDeadline()).toDays()
                        )
                        .dong(matchingPost.getDong())
                        .roomSize(matchingPost.getRoomSize())
                        .matchingStatus(matchingPost.getMatchingStatus())
                        .build()
                )
                .toList();
        return new FindMatchingPostsResponse(matchingPostsData);
    }

    // 매칭글 작성
    @Transactional
    public CreateMatchingPostResponse saveMatchingPost(CreateMatchingPostData dto){
        // 로그인 유저 데이터 가져오기
        // Member author = memberRepository.findById(); // 로그인 구현 완료시 사용
        Member author = new Member();
        // Create MatchingPost Entity
        MatchingPost newMatchingPost = MatchingPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .deadline(dto.getDeadline())
                .dong(dto.getDongType())
                .roomSize(dto.getRoomSizeType())
                .matchingStatus(MatchingStatus.WAITING)
                .author(author)
                .build();
        // persist entity
        matchingPostRepository.save(newMatchingPost);
        Long newMatchingPostId = newMatchingPost.getId();
        return new CreateMatchingPostResponse(newMatchingPostId);
    }

    // 특정 매칭글 조회(ID)
    public FindMatchingPostResponse findByIdMatchingPost(Long matchingPostId){
        MatchingPost matchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingPostId));
        return FindMatchingPostResponse.builder()
                .title(matchingPost.getTitle())
                .content(matchingPost.getContent())
                .dong(matchingPost.getDong())
                .roomSize(matchingPost.getRoomSize())
                .deadline(matchingPost.getDeadline())
                .createdAt(matchingPost.getCreatedAt())
                .matchingStatus(matchingPost.getMatchingStatus())
                .build();
    }

    // 특정 매칭글 삭제
    @Transactional
    public void removeMatchingPost(Long matchingPostId) {
        MatchingPost findMatchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingPostId));
        matchingPostRepository.delete(findMatchingPost);
    }

    // 매칭글 수정
    @Transactional
    public Long updateMatchingPost(Long matchingPostId, UpdateMatchingPostData dto){
        MatchingPost matchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingPostId));
        matchingPost.updateMatchingPost(dto); // dirty checking
        return matchingPostId;
    }

    // 내 매칭글 조회
    public FindMyMatchingPostResponse findMyMatchingPosts(){
        Member author = new Member(); // 임시 인증 유저
        List<MatchingPost> findMatchingPosts = matchingPostQueryRepository.findByMember(author);
        List<FindMyMatchingPostData> findMyMatchingPostData = findMatchingPosts.stream()
                .map(FindMyMatchingPostData::toFindMyMatchingPostData
                ).toList();
        return FindMyMatchingPostResponse.builder()
                .data(findMyMatchingPostData)
                .build();
    }
}
