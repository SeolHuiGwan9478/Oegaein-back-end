package com.likelion.oegaein.domain.chat.dto;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOneToOneChatRoomResponse implements ResponseDto {
    private String chatRoomId;
}
