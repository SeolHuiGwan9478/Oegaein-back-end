package com.likelion.oegaein.repository.member;

import com.likelion.oegaein.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member user){
        em.persist(user);
        return user.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
