package com.likelion.oegaein.domain.matching.repository.query;

import com.likelion.oegaein.domain.matching.entity.MatchingAcceptance;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchingRequestQueryRepository {
    private final EntityManager em;

    public Page<MatchingRequest> findComeMatchingRequests(Member author, Pageable pageable){
        Long authorId = author.getId();
        String jpql = "select mr from MatchingRequest mr" +
                " join fetch mr.matchingPost mrmp" +
                " join fetch mrmp.author mrmpa" +
                " join fetch mrmpa.profile mrmpap" +
                " where mrmpa.id = :authorId" +
                " and mr.matchingAcceptance = :mrstatus" +
                " order by mr.createdAt desc";
        TypedQuery<MatchingRequest> query = em.createQuery(jpql, MatchingRequest.class)
                .setParameter("authorId", authorId)
                .setParameter("mrstatus", MatchingAcceptance.WAITING)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<MatchingRequest> matchingRequests = query.getResultList();
        String countJpql = "select count(mr) from MatchingRequest mr" +
                " join mr.matchingPost mrmp" +
                " join mrmp.author mrmpa" +
                " join mrmpa.profile mrmpap" +
                " where mrmpa.id = :authorId" +
                " and mr.matchingAcceptance = :mrstatus";
        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("mrstatus", MatchingAcceptance.WAITING)
                .getSingleResult();
        return new PageImpl<>(matchingRequests, pageable, total);
    }
    public int countCompletedMatchingRequest(MatchingPost matchingPost){
        Long matchingPostId = matchingPost.getId();
        String jpql = "select count(mr) from MatchingRequest mr" +
                " join mr.matchingPost mrmp" +
                " where mrmp.id = :matchingPostId" +
                " and mr.matchingAcceptance = :matchingAcceptance";
        return em.createQuery(jpql, Long.class)
                .setParameter("matchingPostId", matchingPostId)
                .setParameter("matchingAcceptance", MatchingAcceptance.ACCEPT)
                .getSingleResult()
                .intValue();
    }

    public void bulkUpdateFailedMatchingRequest(List<Long> failedMatchingRequestsId){
        String jpql = "update MatchingRequest mr" +
                " set mr.matchingAcceptance = :matchingacceptance" +
                " where mr.id in :failedmatchingrequestsid";
        em.createQuery(jpql, MatchingRequest.class)
                .setParameter("matchingacceptance", MatchingAcceptance.REJECT)
                .setParameter("failedmatchingrequestsid", failedMatchingRequestsId)
                .executeUpdate();
        em.flush();
        em.clear();
    }
}
