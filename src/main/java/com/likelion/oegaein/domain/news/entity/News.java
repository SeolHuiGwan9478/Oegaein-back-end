package com.likelion.oegaein.domain.news.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private LocalDate createdAt;

    public News(String title, String url, LocalDate createdAt){
        this.title = title;
        this.url = url;
        this.createdAt = createdAt;
    }
}