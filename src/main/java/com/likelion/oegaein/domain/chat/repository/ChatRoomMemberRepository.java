package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.domain.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
