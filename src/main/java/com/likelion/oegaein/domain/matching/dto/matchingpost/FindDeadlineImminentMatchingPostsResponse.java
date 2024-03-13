package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindDeadlineImminentMatchingPostsResponse implements ResponseDto {
    List<FindDeadlineImminentMatchingPostsData> data;
}
