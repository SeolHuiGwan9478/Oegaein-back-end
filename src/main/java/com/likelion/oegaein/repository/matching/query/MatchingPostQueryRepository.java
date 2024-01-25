package com.likelion.oegaein.repository.matching.query;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchingPostQueryRepository {
    private final EntityManager em; // 엔티티 매니저
    public List<MatchingPost> findByMember(Member member){
        String jpql = "select distinct mp from MatchingPost mp"
                + " join fetch mp.matchingRequests mpmr"
                + " join fetch mr.participant mrp"
                + " join fetch p.profile pp";
        return em.createQuery(jpql, MatchingPost.class).getResultList();
    } // matching request fetch join
}
