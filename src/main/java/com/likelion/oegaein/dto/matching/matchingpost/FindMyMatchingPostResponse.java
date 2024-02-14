package com.likelion.oegaein.dto.matching.matchingpost;

import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public class FindMyMatchingPostResponse implements ResponseDto {
    private final List<FindMyMatchingPostData> data;
}
