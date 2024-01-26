package com.likelion.oegaein.dto.matching.matchingpost;

import lombok.Builder;

import java.util.List;

@Builder
public class FindMyMatchingPostResponse {
    private final List<FindMyMatchingPostData> data;
}
