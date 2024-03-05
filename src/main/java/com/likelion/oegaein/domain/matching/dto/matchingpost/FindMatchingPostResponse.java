package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class FindMatchingPostResponse implements ResponseDto {
    private String title; // 제목
    private String content; // 내용
    private DongType dong; // 동 타입
    private RoomSizeType roomSize; // 방 사이즈
    private LocalDate deadline; // 마감일
    private LocalDateTime createdAt; // 생성일
    private MatchingStatus matchingStatus; // 매칭 상태
    // private Long progressRate;
    // private Member author;
}
