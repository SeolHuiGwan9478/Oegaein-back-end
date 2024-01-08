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

    public void save(MatchingPost post){
        em.persist(post);
    }

    public MatchingPost findById(Long id){
        return em.find(MatchingPost.class, id);
    }

    public List<MatchingPost> findAll(){
        return em.createQuery("select mp from MatchingPost mp", MatchingPost.class)
                .getResultList();
    }
}
