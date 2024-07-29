package com.likelion.oegaein.domain.matching.dto.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.matching.dto.matchingpost.FindMatchingPostsData;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GeneralSearchResponse implements ResponseDto {
    private int curePage;
    private int totalPages;
    private List<FindMatchingPostsData> matchingPostsData;
}