package com.likelion.oegaein.domain.member.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LifePattern {
    MORNING("아침형"),
    NIGHT("새벽형");

    private final String value;
}