package com.likelion.oegaein.domain.matching.repository.query;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchingPostQueryRepository {
    private final EntityManager em; // 엔티티 매니저
    private final int STANDARD_RATE = 5; // 베스트 룸메이트 기준

    public List<MatchingPost> findByMember(Member member){
        String jpql = "select distinct mp from MatchingPost mp"
                + " join fetch mp.matchingRequests mpmr"
                + " join fetch mr.participant mrp"
                + " join fetch p.profile pp";
        return em.createQuery(jpql, MatchingPost.class).getResultList();
    } // matching request fetch join

    public Page<MatchingPost> findBestRoomMateMatchingPosts(Pageable pageable){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mpap.score >= :standardRate" +
                " order by mpap.score desc";
        TypedQuery<MatchingPost> query = em.createQuery(jpql, MatchingPost.class)
                .setParameter("standardRate", STANDARD_RATE)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
        // Get the paginated results
        List<MatchingPost> matchingPosts = query.getResultList();

        // Create count query to get the total number of results
        String countJpql = "select count(mp) from MatchingPost mp" +
                " join mp.author mpa" +
                " join mpa.profile mpap" +
                " where mpap.score >= :standardRate";
        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("standardRate", STANDARD_RATE)
                .getSingleResult();
        return new PageImpl<>(matchingPosts, pageable, total);
    }

    public Page<MatchingPost> findMatchingPostsBetweenTwoDates(LocalDate fromDate, LocalDate toDate, Pageable pageable){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mp.deadline between :fromDate and :toDate" +
                " and mp.matchingStatus = :matchingPostStatus" +
                " order by mp.createdAt desc";
        TypedQuery<MatchingPost> query = em.createQuery(jpql, MatchingPost.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("matchingPostStatus", MatchingStatus.WAITING)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
        // Get the paginated results
        List<MatchingPost> matchingPosts = query.getResultList();

        // Create count query to get the total number of results
        String countJpql = "select count(mp) from MatchingPost mp" +
                " join mp.author mpa" +
                " where mp.deadline between :fromDate and :toDate" +
                " and mp.matchingStatus = :matchingPostStatus";
        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("matchingPostStatus", MatchingStatus.WAITING)
                .getSingleResult();
        return new PageImpl<>(matchingPosts, pageable, total);
    }

    public Page<MatchingPost> searchMatchingPost(String content, Pageable pageable){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mp.title like concat('%',:content,'%')" +
                " or mp.content like concat('%',:content,'%')" +
                " or mpap.name like concat('%',:content,'%')" +
                " order by mp.createdAt desc";
        TypedQuery<MatchingPost> query = em.createQuery(jpql, MatchingPost.class)
                .setParameter("content", content)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
        // Get the paginated results
        List<MatchingPost> matchingPosts = query.getResultList();
        // Create count query to get the total number of results
        String countJpql = "select count(mp) from MatchingPost mp" +
                " join mp.author mpa" +
                " join mpa.profile mpap" +
                " where mp.title like concat('%',:content,'%')" +
                " or mp.content like concat('%',:content,'%')" +
                " or mpap.name like concat('%',:content,'%')";
        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("content", content)
                .getSingleResult();
        return new PageImpl<>(matchingPosts, pageable, total);
    }

    public Page<MatchingPost> findAllExceptBlockedMember(List<Long> blockedMemberIds, Pageable pageable){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mpa.id not in :blockedmemberids" +
                " order by mp.createdAt desc";

        // Create query and set pagination parameters
        TypedQuery<MatchingPost> query = em.createQuery(jpql, MatchingPost.class)
                .setParameter("blockedmemberids", blockedMemberIds)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        // Get the paginated results
        List<MatchingPost> matchingPosts = query.getResultList();

        // Create count query to get the total number of results
        String countJpql = "select count(mp) from MatchingPost mp" +
                " join mp.author mpa" +
                " where mpa.id not in :blockedmemberids";
        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("blockedmemberids", blockedMemberIds)
                .getSingleResult();

        // Return the paginated results as a Page object
        return new PageImpl<>(matchingPosts, pageable, total);
    }

    public Page<MatchingPost> findBestRoomMateMatchingPostsExceptBlockedMember(List<Long> blockedMemberIds, Pageable pageable){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mpap.score >= :standardRate" +
                " and mpa.id not in :blockedMemberIds" +
                " order by mpap.score desc";
        TypedQuery<MatchingPost> query = em.createQuery(jpql, MatchingPost.class)
                .setParameter("standardRate", STANDARD_RATE)
                .setParameter("blockedMemberIds", blockedMemberIds)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        // Get the paginated results
        List<MatchingPost> matchingPosts = query.getResultList();

        // Create count query to get the total number of results
        String countJpql = "select count(mp) from MatchingPost mp" +
                " join mp.author mpa" +
                " where mpap.score >= :standardRate" +
                " and mpa.id not in :blockedMemberIds";
        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("standardRate", STANDARD_RATE)
                .setParameter("blockedMemberIds", blockedMemberIds)
                .getSingleResult();
        return new PageImpl<>(matchingPosts, pageable, total);
    }

    public Page<MatchingPost> findMatchingPostsBetweenTwoDatesExceptBlockedMember(LocalDate fromDate, LocalDate toDate, List<Long> blockedMemberIds, Pageable pageable){
        String jpql = "select mp from MatchingPost mp" +
                " join fetch mp.author mpa" +
                " join fetch mpa.profile mpap" +
                " where mp.deadline between :fromDate and :toDate" +
                " and mp.matchingStatus = :matchingPostStatus" +
                " and mpa.id not in :blockedmemberids" +
                " order by mp.createdAt desc";
        TypedQuery<MatchingPost> query = em.createQuery(jpql, MatchingPost.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("matchingPostStatus", MatchingStatus.WAITING)
                .setParameter("blockedmemberids", blockedMemberIds);
        // Get the paginated results
        List<MatchingPost> matchingPosts = query.getResultList();

        // Create count query to get the total number of results
        String countJpql = "select count(mp) from MatchingPost mp" +
                " join mp.author mpa" +
                " where mp.deadline between :fromDate and :toDate" +
                " and mp.matchingStatus = :matchingPostStatus" +
                " and mpa.id not in :blockedmemberids";
        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("matchingPostStatus", MatchingStatus.WAITING)
                .setParameter("blockedmemberids", blockedMemberIds)
                .getSingleResult();
        return new PageImpl<>(matchingPosts, pageable, total);
    }

    public int updateExpiredMatchingPost(){
        String jpql = "update MatchingPost mp set mp.matchingStatus = :matchingPostStatus" +
                " where mp.deadline < :deadlineDate";
        int resultCount = em.createQuery(jpql)
                .setParameter("matchingPostStatus", MatchingStatus.EXPIRED)
                .setParameter("deadlineDate", LocalDate.now())
                .executeUpdate();
        em.flush();
        em.clear();
        return resultCount;
    }
}
