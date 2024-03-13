package com.likelion.oegaein.domain.news.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class News {
    @Id @GeneratedValue
    private Long id;
}
