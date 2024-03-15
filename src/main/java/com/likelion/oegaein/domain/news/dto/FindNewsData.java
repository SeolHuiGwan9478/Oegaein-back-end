package com.likelion.oegaein.domain.news.dto;

import com.likelion.oegaein.domain.news.entity.News;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FindNewsData {
    private Long id;
    private String title;
    private String url;
    private LocalDate createdAt;

    public static FindNewsData toFindNewsData(News news){
        return FindNewsData.builder()
                .id(news.getId())
                .title(news.getTitle())
                .url(news.getUrl())
                .createdAt(news.getCreatedAt())
                .build();
    }
}
