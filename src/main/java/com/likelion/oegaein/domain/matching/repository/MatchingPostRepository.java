package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingPostRepository extends JpaRepository<MatchingPost, Long> {
    List<MatchingPost> findByAuthor(Member author);
}
