package com.likelion.oegaein.dto.matching.matchingpost;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindMatchingPostsResponse {
    List<FindMatchingPostsData> data;
}
