package com.likelion.oegaein.repository.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchingPostRepository {
    private final EntityManager em;

    public void save(MatchingPost matchingPost){
        em.persist(matchingPost);
    }

    public MatchingPost findById(Long matchingPostId){
        return em.find(MatchingPost.class, matchingPostId);
    }

    public List<MatchingPost> findAll(){
        return em.createQuery("select mp from MatchingPost mp", MatchingPost.class)
                .getResultList();
    }

    public void delete(Long matchingPostId){
        MatchingPost findMatchingPost = em.find(MatchingPost.class, matchingPostId);
        em.remove(findMatchingPost);
    }
}
