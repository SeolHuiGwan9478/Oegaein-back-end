package com.likelion.oegaein.domain.matching;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.matching.UpdateMatchingPostData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void updateMatchingPost(UpdateMatchingPostData dto){
        if(!title.equals(dto.getTitle())) title = dto.getTitle();
        if(!content.equals(dto.getContent())) content = dto.getContent();
        if(!deadline.equals(dto.getDeadline())) deadline = dto.getDeadline();
        if(!dong.equals(dto.getDongType())) dong = dto.getDongType();
        if(!roomSize.equals(dto.getRoomSizeType())) roomSize = dto.getRoomSizeType();
    }
}
