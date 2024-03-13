package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MatchingPostRepository extends JpaRepository<MatchingPost, Long> {
    List<MatchingPost> findByAuthor(Member author);
    List<MatchingPost> findByDeadlineBetween(LocalDate fromDate, LocalDate toDate);
}
