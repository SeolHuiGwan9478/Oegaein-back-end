package com.likelion.oegaein.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateOneToOneChatRoomData {
    private Long opponentMemberId;
    public static CreateOneToOneChatRoomData toCreateChatRoomData(CreateOneToOneChatRoomRequest dto){
        return CreateOneToOneChatRoomData.builder()
                .opponentMemberId(dto.getOpponentMemberId())
                .build();
    }
}
