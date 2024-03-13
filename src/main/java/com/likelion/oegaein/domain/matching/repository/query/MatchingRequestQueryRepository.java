package com.likelion.oegaein.domain.matching.repository.query;

import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchingRequestQueryRepository {
    private EntityManager em;

    public List<MatchingRequest> findComeMatchingRequests(Member author){
        Long authorId = author.getId();
        String jpql = "select mr from MatchingRequest mr" +
                " join fetch mr.matchingPost mrmp" +
                " join fetch mrmp.author mrmpa" +
                " join fetch mrmpa.profile mrmpap" +
                " where mrmpa.id = :authorId" +
                " order by mr.createdAt desc";
        return em.createQuery(jpql, MatchingRequest.class).getResultList();
    }
}
