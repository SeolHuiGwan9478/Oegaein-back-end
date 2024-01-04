package com.likelion.oegaein.domain.matching;

import com.likelion.oegaein.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class MatchingPost {
    @Id @GeneratedValue
    @Column(name = "matching_post_id")
    private Long id; // PK

    private String content;
    private LocalDateTime deadline;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus; // test, WAITING, COMPLETED

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User author;
}
