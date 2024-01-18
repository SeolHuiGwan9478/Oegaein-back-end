package com.likelion.oegaein.repository.matching;

import com.likelion.oegaein.domain.matching.MatchingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRequestRepository extends JpaRepository<MatchingRequest, Long> {
}
