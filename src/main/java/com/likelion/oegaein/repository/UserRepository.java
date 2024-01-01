package com.likelion.oegaein.repository;

import com.likelion.oegaein.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(User user){
        em.persist(user);
        return user.getId();
    }

    public User find(Long id){
        return em.find(User.class, id);
    }
}
