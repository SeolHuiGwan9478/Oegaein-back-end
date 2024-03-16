package com.likelion.oegaein.domain.chat.controller;

import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomData;
import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomRequest;
import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomResponse;
import com.likelion.oegaein.domain.chat.service.ChatRoomService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @PostMapping("/api/v1/chatrooms")
    public ResponseEntity<ResponseDto> postChatRoom(CreateOneToOneChatRoomRequest dto){
        log.info("Request to post chatroom");
        CreateOneToOneChatRoomResponse response = chatRoomService.createChatRoom(CreateOneToOneChatRoomData.toCreateChatRoomData(dto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
