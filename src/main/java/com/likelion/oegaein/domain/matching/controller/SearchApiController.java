package com.likelion.oegaein.domain.matching.controller;

import com.likelion.oegaein.domain.matching.dto.search.GeneralSearchResponse;
import com.likelion.oegaein.domain.matching.service.SearchService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SearchApiController {
    private final SearchService searchService;
    @GetMapping("/api/v1/search")
    public ResponseEntity<ResponseDto> searchGeneralPosts(@RequestParam("q") String q, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        log.info("Request to search general posts");
        GeneralSearchResponse response = searchService.searchGeneralPosts(q, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}