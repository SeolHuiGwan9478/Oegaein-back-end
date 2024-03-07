package com.likelion.oegaein.domain.matching.controller;

import com.likelion.oegaein.domain.matching.dto.matchingpost.*;
import com.likelion.oegaein.domain.matching.service.MatchingPostService;
import com.likelion.oegaein.domain.member.service.MemberService;
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
    public ResponseEntity<FindMatchingPostsResponse> getMatchingPosts(){
        log.info("Request to get matchingposts"); // logging
        FindMatchingPostsResponse response = matchingPostService.findAllMatchingPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/matchingposts") // 매칭 글 등록
    public ResponseEntity<CreateMatchingPostResponse> postMatchingPost(@RequestBody CreateMatchingPostRequest dto){
        log.info("Request to post matchingpost");
        CreateMatchingPostData convertedDto = CreateMatchingPostData.toCreateMatchingPostData(dto);
        CreateMatchingPostResponse response = matchingPostService.saveMatchingPost(convertedDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/matchingposts/{matchingpostid}") // 특정 매칭 글 조회
    public ResponseEntity<FindMatchingPostResponse> getMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to get matchingpost by id-{}", matchingPostId);
        try{
            FindMatchingPostResponse response = matchingPostService.findByIdMatchingPost(matchingPostId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/v1/matchingposts/{matchingpostid}")
    public ResponseEntity<Long> deleteMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to delete matchingpost by id-{}", matchingPostId);
        try{
            matchingPostService.removeMatchingPost(matchingPostId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/v1/matchingposts/{matchingpostid}")
    public ResponseEntity<UpdateMatchingPostResponse> putMatchingPost(@PathVariable("matchingpostid") Long matchingPostId,
                                                  @RequestBody UpdateMatchingPostRequest dto){
        try{
            UpdateMatchingPostData convertedDto = UpdateMatchingPostData.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .deadline(dto.getDeadline())
                    .dongType(dto.getDongType())
                    .roomSizeType(dto.getRoomSizeType())
                    .build();
            Long updatedMatchingPost = matchingPostService.updateMatchingPost(matchingPostId, convertedDto);
            UpdateMatchingPostResponse response = new UpdateMatchingPostResponse(updatedMatchingPost);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/v1/my-matchingposts")
    public ResponseEntity<FindMyMatchingPostResponse> getMyMatchingPosts(){
        log.info("Request to get my matching posts");
        try{
            FindMyMatchingPostResponse response = matchingPostService.findMyMatchingPosts();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){ // 에러 수정 필요
            return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
}
