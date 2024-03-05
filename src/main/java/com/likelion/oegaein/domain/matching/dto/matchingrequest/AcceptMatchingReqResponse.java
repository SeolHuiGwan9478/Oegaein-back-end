package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AcceptMatchingReqResponse implements ResponseDto {
    private Long matchingRequestId; // 매칭 요청 ID
    private Long chatRoomNo; // 채팅방 번호
}
