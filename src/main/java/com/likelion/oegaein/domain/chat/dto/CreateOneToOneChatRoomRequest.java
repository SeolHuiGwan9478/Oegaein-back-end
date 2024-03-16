package com.likelion.oegaein.domain.chat.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOneToOneChatRoomRequest {
    private Long opponentMemberId; // 상대방 ID
}
