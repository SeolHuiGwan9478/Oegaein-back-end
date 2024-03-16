package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
}
