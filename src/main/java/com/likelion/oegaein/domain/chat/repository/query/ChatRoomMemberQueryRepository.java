package com.likelion.oegaein.domain.chat.repository.query;

import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberQueryRepository {
    private final EntityManager em;

    public List<ChatRoomMember> findNeedToRemoveChatRooms(Long blockingId, Long blockedId){
        String jpql = "select crm from ChatRoomMember crm" +
                " join fetch crm.chatRoom crmc" +
                " join crmc.matchingPost crmcm" +
                " join crmcm.author crmcma" +
                " where crm.member.id = :blockedId" +
                " and crmcma.id = :blockingId";
        return em.createQuery(jpql, ChatRoomMember.class)
                .setParameter("blockedId", blockedId)
                .setParameter("blockingId", blockingId)
                .getResultList();
    }
}