package com.likelion.oegaein.repository.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MatchingPostRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(MatchingPost post){
        em.persist(post);
        return post.getId();
    }

    public MatchingPost find(Long id){
        return em.find(MatchingPost.class, id);
    }
}
