package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.matchingpost.*;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.query.MatchingPostQueryRepository;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
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
                .map(FindMatchingPostsData::toFindMatchingPostsData)
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
                .dongType(dto.getDongType())
                .roomSizeType(dto.getRoomSizeType())
                .matchingStatus(MatchingStatus.WAITING)
                .author(author)
                .build();
        // persist entity
        matchingPostRepository.save(newMatchingPost);
        return new CreateMatchingPostResponse(newMatchingPost.getId());
    }

    // 특정 매칭글 조회(ID)
    public FindMatchingPostResponse findByIdMatchingPost(Long matchingPostId){
        MatchingPost matchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingPostId));
        return FindMatchingPostResponse.toFindMatchingPostResponse(matchingPost);
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
    public UpdateMatchingPostResponse updateMatchingPost(Long matchingPostId, UpdateMatchingPostData dto){
        MatchingPost matchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: " + matchingPostId));
        matchingPost.updateMatchingPost(dto); // dirty checking
        return new UpdateMatchingPostResponse(matchingPostId);
    }

    // 내 매칭글 조회
    public FindMyMatchingPostResponse findMyMatchingPosts(){
        Member author = new Member(); // 임시 인증 유저
        List<MatchingPost> findMatchingPosts = matchingPostRepository.findByAuthor(author);
        List<FindMyMatchingPostData> findMyMatchingPostData = findMatchingPosts.stream()
                .map(FindMyMatchingPostData::toFindMyMatchingPostData
                ).toList();
        return FindMyMatchingPostResponse.builder()
                .data(findMyMatchingPostData)
                .build();
    }

    // 베스트 룸메이트 매칭글 조회
    public FindBestRoomMateMatchingPostResponse findBestRoomMateMatchingPosts(){
        List<MatchingPost> findMatchingPosts = matchingPostQueryRepository.findBestRoomMateMatchingPosts();
        List<FindBestRoomMateMatchingPostData> bestRoomMateMatchingPostDatas = findMatchingPosts.stream()
                .map(FindBestRoomMateMatchingPostData::toFindBestRoomMateMatchingPostData)
                .toList();
        return new FindBestRoomMateMatchingPostResponse(bestRoomMateMatchingPostDatas);
    }
}