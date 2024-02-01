package com.likelion.oegaein.repository.matching;

import com.likelion.oegaein.domain.matching.MatchingRequest;
import com.likelion.oegaein.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingRequestRepository extends JpaRepository<MatchingRequest, Long> {
    List<MatchingRequest> findByParticipant(Optional<Member> participant);
}
