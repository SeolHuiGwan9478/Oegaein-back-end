package com.likelion.oegaein.domain.matching.controller;

import com.likelion.oegaein.domain.matching.dto.matchingpost.*;
import com.likelion.oegaein.domain.matching.service.MatchingPostService;
import com.likelion.oegaein.domain.member.service.MemberService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchingPostApiController {
    private final MatchingPostService matchingPostService;

    @GetMapping("/api/v1/matchingposts") // 전체 매칭 글 조회
    public ResponseEntity<ResponseDto> getMatchingPosts(){
        log.info("Request to get matchingposts"); // logging
        FindMatchingPostsResponse response = matchingPostService.findAllMatchingPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/matchingposts") // 매칭 글 등록
    public ResponseEntity<ResponseDto> postMatchingPost(@RequestBody CreateMatchingPostRequest dto){
        log.info("Request to post matchingpost");
        CreateMatchingPostData convertedDto = CreateMatchingPostData.toCreateMatchingPostData(dto);
        CreateMatchingPostResponse response = matchingPostService.saveMatchingPost(convertedDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/matchingposts/{matchingpostid}") // 특정 매칭 글 조회
    public ResponseEntity<ResponseDto> getMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to get matchingpost by id-{}", matchingPostId);
        FindMatchingPostResponse response = matchingPostService.findByIdMatchingPost(matchingPostId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/matchingposts/{matchingpostid}")
    public ResponseEntity<ResponseDto> deleteMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to delete matchingpost by id-{}", matchingPostId);
        matchingPostService.removeMatchingPost(matchingPostId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/v1/matchingposts/{matchingpostid}")
    public ResponseEntity<ResponseDto> putMatchingPost(@PathVariable("matchingpostid") Long matchingPostId,
                                                  @RequestBody UpdateMatchingPostRequest dto){
        UpdateMatchingPostData convertedDto = UpdateMatchingPostData.toUpdateMatchingPostData(dto);
        UpdateMatchingPostResponse response = matchingPostService.updateMatchingPost(matchingPostId, convertedDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/v1/my-matchingposts")
    public ResponseEntity<ResponseDto> getMyMatchingPosts(){
        log.info("Request to get my matching posts");
        FindMyMatchingPostResponse response = matchingPostService.findMyMatchingPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/v1/best-roommate-matchingposts")
    public ResponseEntity<ResponseDto> getBestRoomMateMatchingPosts(){
        log.info("Request to get best-roommate matching posts");
        FindBestRoomMateMatchingPostResponse response = matchingPostService.findBestRoomMateMatchingPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
