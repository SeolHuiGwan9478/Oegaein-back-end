package com.likelion.oegaein.controller.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.dto.matching.*;
import com.likelion.oegaein.service.matching.MatchingService;
import com.likelion.oegaein.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchingPostController {
    private final MatchingService matchingService;
    private final MemberService memberService;

    @GetMapping("/api/v1/matchingposts") // 전체 매칭 글 조회
    public ResponseEntity<FindMatchingPostsResponse> getMatchingPosts(){
        log.info("Request to get matchingposts"); // logging
        FindMatchingPostsResponse response = matchingService.findAllMatchingPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/matchingposts") // 매칭 글 등록
    public ResponseEntity<CreateMatchingPostResponse> postMatchingPost(@RequestBody CreateMatchingPostRequest dto){
        log.info("Request to post matchingpost");
        CreateMatchingPostData convertedDto = CreateMatchingPostData.toCreateMatchingPostData(dto);
        CreateMatchingPostResponse response = matchingService.saveMatchingPost(convertedDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/matchingposts/{matchingpostid}") // 특정 매칭 글 조회
    public ResponseEntity<FindMatchingPostResponse> getMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to get matchingpost by id-{}", matchingPostId);
        try{
            FindMatchingPostResponse response = matchingService.findByIdMatchingPost(matchingPostId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/v1/matchingposts/{matchingpostid}")
    public ResponseEntity<Long> deleteMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to delete matchingpost by id-{}", matchingPostId);
        try{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/v1/matchingPosts/{matchingpostid}")
    public ResponseEntity<Long> patchMatchingPost(@PathVariable("matchingpostid") Long matchingPostId,
                                                  @RequestBody UpdateMatchingPostRequest dto){
        try{
            UpdateMatchingPostData changedDto = UpdateMatchingPostData.builder()
                    .matchingPostId(matchingPostId)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .deadline(dto.getDeadline())
                    .dongType(dto.getDongType())
                    .roomSizeType(dto.getRoomSizeType())
                    .build();
            Long updatedMatchingPost = matchingService.updateMatchingPost(changedDto);
            return new ResponseEntity<>(updatedMatchingPost, HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
