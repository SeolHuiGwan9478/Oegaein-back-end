package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.matchingpost.FindMatchingPostsData;
import com.likelion.oegaein.domain.matching.dto.search.GeneralSearchResponse;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.query.MatchingPostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
    private final MatchingPostQueryRepository matchingPostQueryRepository;
    // 공동배달 레포지토리

    public GeneralSearchResponse searchGeneralPosts(String content, Pageable pageable){
        Page<MatchingPost> result = matchingPostQueryRepository.searchMatchingPost(content, pageable);
        List<MatchingPost> findMatchingPosts = result.getContent();
        int curPage = result.getNumber();
        int totalPages = result.getTotalPages();
        List<FindMatchingPostsData> findMatchingPostsData = findMatchingPosts.stream()
                .map(FindMatchingPostsData::toFindMatchingPostsData).toList();
        return GeneralSearchResponse.builder()
                .curPage(curPage)
                .totalPages(totalPages)
                .matchingPostsData(findMatchingPostsData)
                .build();
    }
}
