package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public class FindMyMatchingPostResponse implements ResponseDto {
    private final List<FindMyMatchingPostData> data;
}
