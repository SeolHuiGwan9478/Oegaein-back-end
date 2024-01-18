package com.likelion.oegaein.domain.matching;

import com.likelion.oegaein.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MatchingRequest {
    @Id @GeneratedValue
    @Column(name = "matching_req_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_post_id")
    private MatchingPost matchingPost;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Member participant;

    protected MatchingRequest(){}
    public MatchingRequest(MatchingPost matchingPost, Member participant){
        this.matchingPost = matchingPost;
        this.participant = participant;
    }
}
