package com.likelion.oegaein.domain.matching.util;

import com.likelion.oegaein.domain.matching.repository.query.MatchingPostQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckExpirationScheduler {
    private final MatchingPostQueryRepository matchingPostQueryRepository;
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void checkExpirationMatchingPost(){
        log.info("Check Expiration MatchingPosts");
        int resultCount = matchingPostQueryRepository.updateExpiredMatchingPost();
        log.info("Update MatchingPosts({})", resultCount);
    }
}