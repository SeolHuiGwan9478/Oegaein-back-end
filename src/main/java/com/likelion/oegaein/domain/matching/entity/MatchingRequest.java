package com.likelion.oegaein.domain.matching.entity;

import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.matching.exception.MatchingRequestException;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class MatchingRequest {
    @Id @GeneratedValue
    @Column(name = "matching_req_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_post_id")
    private MatchingPost matchingPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Member participant;

    @Enumerated(EnumType.STRING)
    private MatchingAcceptance matchingAcceptance; // 매칭 수락 여부 : 수락/대기/거부

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected MatchingRequest(){}
    public MatchingRequest(MatchingPost matchingPost, Member participant){
        this.matchingPost = matchingPost;
        this.participant = participant;
        this.matchingAcceptance = MatchingAcceptance.WAITING;
    }

    public void acceptMatchingRequest(){
        this.matchingAcceptance = MatchingAcceptance.ACCEPT;
    }

    public void rejectMatchingRequest(){
        this.matchingAcceptance = MatchingAcceptance.REJECT;
    }

    public void failedMatchingRequest(){
        if(this.matchingAcceptance.equals(MatchingAcceptance.WAITING)){
            this.matchingAcceptance = MatchingAcceptance.REJECT;
        }
    }
}
