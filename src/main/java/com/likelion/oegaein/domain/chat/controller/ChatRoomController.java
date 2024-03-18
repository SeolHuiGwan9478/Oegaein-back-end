package com.likelion.oegaein.domain.chat.controller;

import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomData;
import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomRequest;
import com.likelion.oegaein.domain.chat.dto.CreateOneToOneChatRoomResponse;
import com.likelion.oegaein.domain.chat.dto.DeleteOneToOneChatRoomResponse;
import com.likelion.oegaein.domain.chat.service.ChatRoomService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @PostMapping("/api/v1/onetoone-chatrooms")
    public ResponseEntity<ResponseDto> postChatRoom(CreateOneToOneChatRoomRequest dto){
        log.info("Request to post chatroom");
        CreateOneToOneChatRoomResponse response = chatRoomService.createOneToOneChatRoom(CreateOneToOneChatRoomData.toCreateChatRoomData(dto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/onetoone-chatrooms/{roomid}")
    public ResponseEntity<ResponseDto> deleteChatRoom(@PathVariable("roomid") String roomId){
        log.info("Request to delete chatroom-{}", roomId);
        DeleteOneToOneChatRoomResponse response = chatRoomService.removeOneToOneChatRoom(roomId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
