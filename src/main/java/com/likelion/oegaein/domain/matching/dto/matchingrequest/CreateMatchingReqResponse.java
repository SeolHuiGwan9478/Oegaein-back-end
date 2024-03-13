package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateMatchingReqResponse implements ResponseDto {
    private final Long matchingRequestId;
}
