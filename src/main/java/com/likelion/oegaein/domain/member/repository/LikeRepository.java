package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.member.Likey;
import com.likelion.oegaein.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likey, Long> {
    boolean existsBySenderAndReceiver(Member sender, Member receiver);
    Optional<List<Likey>> findBySender(Member sender);
    Optional<Likey> findBySenderAndReceiver(Member sender, Member receiver);
}