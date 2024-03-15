package com.likelion.oegaein.domain.news.dto;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindNewsResponse implements ResponseDto {
    List<FindNewsData> data;
}
