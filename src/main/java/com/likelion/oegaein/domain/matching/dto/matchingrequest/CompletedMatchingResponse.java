package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompletedMatchingResponse implements ResponseDto {
    private String chatRoomNo; // 채팅방 번호
}