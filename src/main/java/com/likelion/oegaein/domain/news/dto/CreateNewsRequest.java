package com.likelion.oegaein.domain.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateNewsRequest {
    private List<CreateNewsData> data;
}
