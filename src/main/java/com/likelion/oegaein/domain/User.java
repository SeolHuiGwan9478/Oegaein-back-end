package com.likelion.oegaein.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {
    @Id @GeneratedValue
    private Long id;
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
