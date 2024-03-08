package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingRequestRepository extends JpaRepository<MatchingRequest, Long> {
    List<MatchingRequest> findByParticipant(Member participant);
}
