package com.likelion.oegaein.domain.matching;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.matching.UpdateMatchingPostData;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class MatchingPost {
    @Id @GeneratedValue
    @Column(name = "matching_post_id")
    private Long id; // PK

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private DongType dong; // 기숙사 동(* A,B,C,D,E)
    @Enumerated(EnumType.STRING)
    private RoomSizeType roomSize; // 기숙사 방 사이즈(* 2인실/4인실)

    private LocalDateTime deadline;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus; // 매칭 상태, WAITING, COMPLETED

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member author;

    public void matchingPostUpdate(UpdateMatchingPostData dto){
        if(dto.getTitle() != null) this.title = dto.getTitle();
        if(dto.getContent() != null) this.content = dto.getContent();
        if(dto.getDeadline() != null) this.deadline = dto.getDeadline();
        if(dto.getDongType() != null) this.dong = dto.getDongType();
        if(dto.getRoomSizeType() != null) this.roomSize = dto.getRoomSizeType();
    }
}
