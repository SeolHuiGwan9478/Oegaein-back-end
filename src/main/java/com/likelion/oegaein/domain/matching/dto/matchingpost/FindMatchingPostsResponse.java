package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.dto.global.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindMatchingPostsResponse implements ResponseDto {
    List<FindMatchingPostsData> data;
}
