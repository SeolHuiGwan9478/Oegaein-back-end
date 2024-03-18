package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.domain.ChatRoom;
import com.likelion.oegaein.domain.chat.domain.ChatRoomMember;
import com.likelion.oegaein.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
