package com.likelion.oegaein.chat;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.chat.repository.ChatRoomRepository;
import com.likelion.oegaein.domain.chat.service.ChatRoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChatRoomTest {
//    @Autowired
//    ChatRoomService chatRoomService;
//
//    @Autowired
//    ChatRoomRepository chatRoomRepository;
//
//    @Test
//    @DisplayName("동시성 제어 실패 테스트")
//    @Rollback(value = true)
//    public void 비관적_락킹_미사용_테스트() throws InterruptedException {
//        int count = 20;
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        CountDownLatch latch = new CountDownLatch(count);
//        for(int i = 0;i < count;i++){
//            executorService.execute(() -> {
//                try{
//                    chatRoomService.increaseMemberCount(1L);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                latch.countDown();
//            });
//        }
//        latch.await();
//
//        ChatRoom chatRoom = chatRoomRepository.findById(1L).get();
//        org.assertj.core.api.Assertions.assertThat(chatRoom.getMemberCount()).isNotEqualTo(21);
//    }
//
//    @Test
//    @DisplayName("동시성 제어 성공 - 비관적 락")
//    @Rollback(value = true)
//    public void 비관적_락킹_사용_테스트() throws InterruptedException {
//        int count = 20;
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        CountDownLatch latch = new CountDownLatch(count);
//        for(int i = 0;i < count;i++){
//            executorService.execute(() -> {
//                try{
//                    chatRoomService.increaseMemberCountPessimisticLock(1L);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                latch.countDown();
//            });
//        }
//        latch.await();
//
//        ChatRoom chatRoom = chatRoomRepository.findById(1L).get();
//        org.assertj.core.api.Assertions.assertThat(chatRoom.getMemberCount()).isEqualTo(21);
//    }
}
