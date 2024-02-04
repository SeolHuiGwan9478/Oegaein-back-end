package com.likelion.oegaein.repository.member;

import com.likelion.oegaein.domain.member.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
