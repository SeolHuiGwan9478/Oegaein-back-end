package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.matching.dto.comment.FindCommentData;
import com.likelion.oegaein.domain.matching.entity.*;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindMatchingPostResponse implements ResponseDto {
    private Long id;
    private String title; // 제목
    private String content; // 내용
    private DongType dong; // 동 타입
    private RoomSizeType roomSize; // 방 사이즈
    private int targetNumberOfPeople; // 모집 인원
    private LocalDate deadline; // 마감일
    private LocalDateTime createdAt; // 생성일
    private MatchingStatus matchingStatus; // 매칭 상태
    private FindMatchingPostResInProfile authorProfile; // 작성자 프로필
    private int commentsCount; // 댓글 개수
    private List<FindCommentData> comments; // 자식 댓글
    private String authorName; // 작성자 이름
    private Long matchingRequestId;

    public static FindMatchingPostResponse toFindMatchingPostResponse(MatchingPost matchingPost, MatchingRequest matchingRequest){
        Profile findProfile = matchingPost.getAuthor().getProfile();
        FindMatchingPostResInProfile convertedProfile = FindMatchingPostResInProfile.toFindMatchingPostResInProfile(findProfile);
        List<MatchingPostComment> findComments = matchingPost.getComments();
        List<FindCommentData> convertedComments = findComments.stream().map(FindCommentData::toFindCommentData).toList();
        int calcCommentsCount = convertedComments.size() + convertedComments.stream()
                .mapToInt((cc) -> cc.getReplies().size()).sum();
        FindMatchingPostResponse response = FindMatchingPostResponse.builder()
                .id(matchingPost.getId())
                .title(matchingPost.getTitle())
                .content(matchingPost.getContent())
                .dong(matchingPost.getDongType())
                .roomSize(matchingPost.getRoomSizeType())
                .targetNumberOfPeople(matchingPost.getTargetNumberOfPeople())
                .deadline(matchingPost.getDeadline())
                .createdAt(matchingPost.getCreatedAt())
                .matchingStatus(matchingPost.getMatchingStatus())
                .authorProfile(convertedProfile)
                .commentsCount(calcCommentsCount)
                .comments(convertedComments)
                .authorName(findProfile.getName())
                .build();
        if(matchingRequest != null){
            response.setMatchingRequestId(matchingRequest.getId());
        }
        return response;
    }
}
