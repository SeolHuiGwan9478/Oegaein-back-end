package com.likelion.oegaein.domain.chat.service;

import com.likelion.oegaein.domain.chat.domain.ChatRoom;
import com.likelion.oegaein.domain.chat.domain.ChatRoomMember;
import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomData;
import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomResponse;
import com.likelion.oegaein.domain.chat.repository.ChatRoomMemberRepository;
import com.likelion.oegaein.domain.chat.repository.ChatRoomRepository;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.query.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;

    private final String CHAT_ROOM_NAME_SEP = ",";
    private final Long ONE_TO_ONE_CHAT_ROOM_MEMBER_COUNT = 2L;

    @Transactional
    public CreateOneToOneChatRoomResponse createChatRoom(CreateOneToOneChatRoomData dto){
        // find chat members
        Long authenticatedMemberId = 1L; // 임시 인증 유저 ID
        Long opponentMemberId = dto.getOpponentMemberId(); // 상대방 ID
        Member authenticatedMember = memberRepository.findById(authenticatedMemberId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: member")); // 에러 메시지 국제화 필요
        Member opponentMember = memberRepository.findById(opponentMemberId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found: member"));
        List<Member> chatMembers = List.of(authenticatedMember, opponentMember);
        // create chat room
        String chatRoomName = opponentMember.getProfile().getName();
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .chatRoomName(chatRoomName)
                .memberCount(ONE_TO_ONE_CHAT_ROOM_MEMBER_COUNT)
                .build();
        chatRoomRepository.save(chatRoom);
        // create chat room member
        chatMembers.forEach((chatMember) -> {
            ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                    .member(chatMember)
                    .chatRoom(chatRoom)
                    .build();
            chatRoomMemberRepository.save(chatRoomMember);
        });
        return new CreateOneToOneChatRoomResponse(chatRoom.getChatRoomId());
    }
}
