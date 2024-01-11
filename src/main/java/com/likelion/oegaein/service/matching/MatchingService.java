package com.likelion.oegaein.service.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.repository.matching.MatchingPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {
    private final MatchingPostRepository matchingPostRepository;

    // 매칭글 작성
    @Transactional
    public Long post(MatchingPost matchingPost){
        matchingPostRepository.save(matchingPost);
        return matchingPost.getId();
    }

    // 모든 매칭글 조회
    public List<MatchingPost> findMatchingPosts(){
        return matchingPostRepository.findAll();
    }
    // 특정 매칭글 조회(ID)
    public MatchingPost findById(Long matchingPostId){
        return matchingPostRepository.findById(matchingPostId);
    }
}
