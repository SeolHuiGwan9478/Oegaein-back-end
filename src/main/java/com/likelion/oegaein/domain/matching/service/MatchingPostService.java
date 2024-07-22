package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.matchingpost.*;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.MatchingRequestRepository;
import com.likelion.oegaein.domain.matching.repository.query.MatchingPostQueryRepository;
import com.likelion.oegaein.domain.matching.repository.query.MatchingRequestQueryRepository;
import com.likelion.oegaein.domain.matching.validation.MatchingPostValidator;
import com.likelion.oegaein.domain.member.entity.member.Block;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.repository.BlockRepository;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.validation.BlockValidator;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingPostService {
    // constants
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_MATCHING_POST_ERR_MSG = "찾을 수 없는 매칭글입니다.";
    // repository & validator
    private final MatchingPostRepository matchingPostRepository;
    private final MatchingPostQueryRepository matchingPostQueryRepository;
    private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;
    // validators
    private final MatchingPostValidator matchingPostValidator;
    private final MemberValidator memberValidator;
    private final BlockValidator blockValidator;

    // 모든 매칭글 조회
    public FindMatchingPostsResponse findAllMatchingPosts(Authentication authentication, Pageable limit){
        // find matchingPosts
        List<MatchingPost> matchingPosts;
        Page<MatchingPost> result;
        int totalPages;
        int curPage = limit.getPageNumber();
        if (authentication != null){ // find member except black list members
            Member member = memberRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
            List<Long> blackList = getBlackList(member);
            if(blackList.isEmpty()){
                result = matchingPostRepository.findAll(limit);
                matchingPosts = result.getContent();
                totalPages = result.getTotalPages();
            }
            else{
                result = matchingPostQueryRepository.findAllExceptBlockedMember(blackList, limit);
                matchingPosts = result.getContent();
                totalPages = result.getTotalPages();
            }
        }else{
            result = matchingPostRepository.findAll(limit);
            matchingPosts = result.getContent();
            totalPages = result.getTotalPages();
        }
        // create matchingPostsData
        List<FindMatchingPostsData> matchingPostsData = matchingPosts.stream()
                .map(FindMatchingPostsData::toFindMatchingPostsData)
                .toList();

        return new FindMatchingPostsResponse(totalPages, curPage, matchingPostsData);
    }

    // 매칭글 작성
    @Transactional
    public CreateMatchingPostResponse saveMatchingPost(Authentication authentication, CreateMatchingPostData dto){
        matchingPostValidator.validateRoomSizeAndTargetNumOfPeople(dto.getRoomSizeType(), dto.getTargetNumberOfPeople());
        Member author = memberRepository.findByEmail(authentication.getName())
                 .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        // Create MatchingPost Entity
        MatchingPost newMatchingPost = MatchingPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .deadline(dto.getDeadline())
                .targetNumberOfPeople(dto.getTargetNumberOfPeople())
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
    public FindMatchingPostResponse findByIdMatchingPost(Long matchingPostId, Authentication authentication){
        MatchingPost matchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        if(authentication != null){
            Member member = memberRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
            blockValidator.validateBlockedMember(member.getId(), matchingPost.getAuthor().getId());
            blockValidator.validateBlockedMember(matchingPost.getAuthor().getId(), member.getId());
        }
        return FindMatchingPostResponse.toFindMatchingPostResponse(matchingPost);
    }

    // 특정 매칭글 삭제
    @Transactional
    public void removeMatchingPost(Long matchingPostId, Authentication authentication) {
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPost findMatchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        memberValidator.validateIsOwnerMatchingPost(authenticatedMember.getId(), findMatchingPost.getAuthor().getId());
        matchingPostRepository.delete(findMatchingPost);
    }

    // 매칭글 수정
    @Transactional
    public UpdateMatchingPostResponse updateMatchingPost(Long matchingPostId, UpdateMatchingPostData dto, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPost findMatchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        memberValidator.validateIsOwnerMatchingPost(authenticatedMember.getId(), findMatchingPost.getAuthor().getId());
        matchingPostValidator.validateRoomSizeAndTargetNumOfPeople(dto.getRoomSizeType(), dto.getTargetNumberOfPeople());
        findMatchingPost.updateMatchingPost(dto); // dirty checking
        return new UpdateMatchingPostResponse(matchingPostId);
    }

    @Transactional
    public CompleteMatchingPostResponse completeMatchingPost(Long matchingPostId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPost findMatchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        memberValidator.validateIsOwnerMatchingPost(authenticatedMember.getId(), findMatchingPost.getAuthor().getId());
        memberValidator.validateIsAlreadyCompleted(findMatchingPost.getMatchingStatus());
        findMatchingPost.completeMatchingPost();
        return new CompleteMatchingPostResponse(matchingPostId);
    }

    // 내 매칭글 조회
    public FindMyMatchingPostResponse findMyMatchingPosts(Authentication authentication, Pageable pageable){
        Member author = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Page<MatchingPost> result = matchingPostRepository.findByAuthor(author, pageable);
        List<MatchingPost> findMatchingPosts = result.getContent();
        List<FindMyMatchingPostData> findMyMatchingPostData = findMatchingPosts.stream()
                .map(FindMyMatchingPostData::toFindMyMatchingPostData
                ).toList();
        return new FindMyMatchingPostResponse(
                result.getNumber(),
                result.getTotalPages(),
                findMyMatchingPostData
        );
    }

    // 베스트 룸메이트 매칭글 조회
    public FindBestRoomMateMatchingPostsResponse findBestRoomMateMatchingPosts(Authentication authentication, Pageable pageable){
        // find matchingPosts
        Page<MatchingPost> result;
        if (authentication != null){ // find member except black list members
            Member member = memberRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
            List<Long> blackList = getBlackList(member);
            if(blackList.isEmpty()) result = matchingPostQueryRepository.findBestRoomMateMatchingPosts(pageable);
            else result = matchingPostQueryRepository.findBestRoomMateMatchingPostsExceptBlockedMember(blackList, pageable);
        }else{
            result = matchingPostQueryRepository.findBestRoomMateMatchingPosts(pageable);
        }
        List<MatchingPost> findMatchingPosts = result.getContent();
        List<FindBestRoomMateMatchingPostsData> bestRoomMateMatchingPostsData = findMatchingPosts.stream()
                .map(FindBestRoomMateMatchingPostsData::toFindBestRoomMateMatchingPostData)
                .toList();
        return new FindBestRoomMateMatchingPostsResponse(
                result.getNumber(),
                result.getTotalPages(),
                bestRoomMateMatchingPostsData
        );
    }

    public FindDeadlineImminentMatchingPostsResponse findDeadlineImminentMatchingPosts(Authentication authentication, Pageable pageable){
        // find matchingPosts
        Page<MatchingPost> result;
        LocalDate currentDate = LocalDate.now();
        LocalDate beforeOneDayDate = LocalDate.now().plusDays(1);
        if (authentication != null){ // find member except black list members
            Member member = memberRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
            List<Long> blackList = getBlackList(member);
            if(blackList.isEmpty()) result = matchingPostQueryRepository.findMatchingPostsBetweenTwoDates(
                    beforeOneDayDate,
                    currentDate,
                    pageable
            );
            else result = matchingPostQueryRepository.findMatchingPostsBetweenTwoDatesExceptBlockedMember(
                    beforeOneDayDate,
                    currentDate,
                    blackList,
                    pageable
            );
        }else{
            result = matchingPostQueryRepository.findMatchingPostsBetweenTwoDates(
                    beforeOneDayDate,
                    currentDate,
                    pageable
            );
        }
        List<MatchingPost> findMatchingPosts = result.getContent();
        List<FindDeadlineImminentMatchingPostsData> deadlineImminentMatchingPostsData = findMatchingPosts.stream()
                .map(FindDeadlineImminentMatchingPostsData::toFindDeadlineImminentMatchingPostsData)
                .toList();
        return new FindDeadlineImminentMatchingPostsResponse(
                result.getNumber(),
                result.getTotalPages(),
                deadlineImminentMatchingPostsData
        );
    }

    public FindOtherMatchingPostsResponse findOtherMatchingPosts(Long userId, Pageable pageable){
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG)
        );
        Page<MatchingPost> result = matchingPostRepository.findByAuthor(member, pageable);
        int curPage = result.getNumber();
        int totalPages = result.getTotalPages();
        List<MatchingPost> matchingPosts = result.getContent();
        List<FindOtherMatchingPostsData> data = matchingPosts.stream().map(FindOtherMatchingPostsData::toFindOtherMatchingPostsData).toList();
        return new FindOtherMatchingPostsResponse(curPage, totalPages, data);
    }

    // 사용자 정의 함수
    private List<Long> getBlackList(Member member){
        List<Block> blocking = blockRepository.findByBlocking(member); // 내가 차단한 사람 목록
        List<Block> blocked = blockRepository.findByBlocked(member); // 나를 차단한 사람 목록
        List<Long> blockingList = blocking.stream().map((block) -> block.getBlocked().getId()).toList();
        List<Long> blockedList = blocked.stream().map((block) -> block.getBlocking().getId()).toList();
        Set<Long> blackSet = new LinkedHashSet<>(blockingList);
        blackSet.addAll(blockedList);
        return new ArrayList<>(blackSet);
    }
}