package com.likelion.oegaein.domain.news.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Builder
public class CreateNewsData {
    private String title;
    private String url;
    private LocalDate createdAt;
}
