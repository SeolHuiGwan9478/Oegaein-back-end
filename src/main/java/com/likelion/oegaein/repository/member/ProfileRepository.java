package com.likelion.oegaein.repository.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.domain.member.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByMember(Member member);
    Optional<Member> findByName(String name);
}
