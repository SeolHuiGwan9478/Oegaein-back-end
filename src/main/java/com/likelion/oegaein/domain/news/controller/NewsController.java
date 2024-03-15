package com.likelion.oegaein.domain.news.controller;

import com.likelion.oegaein.domain.news.dto.FindNewsResponse;
import com.likelion.oegaein.domain.news.service.NewsService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/api/v1/news")
    public ResponseEntity<ResponseDto> getNews(){
        log.info("Request to get dorm news");
        FindNewsResponse response = newsService.findNews();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}