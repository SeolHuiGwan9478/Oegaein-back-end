package com.likelion.oegaein.domain.matching.dto.matchingrequest;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindMyMatchingReqsResponse implements ResponseDto {
    private int count;
    private int curPage;
    private int totalPages;
    private List<FindMyMatchingReqData> data;
}
