package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindComeMatchingReqsResponse implements ResponseDto {
    private int count;
    private List<FindComeMatchingReqData> data;
}
