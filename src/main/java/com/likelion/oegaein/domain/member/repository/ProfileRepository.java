package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String name);
}
