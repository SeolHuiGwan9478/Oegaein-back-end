package com.likelion.oegaein.controller.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.matching.CreateMatchingPostRequest;
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
    public List<MatchingPost> getMatchingPosts(){
        log.info("Request to get matchingposts"); // logging
        return matchingService.findAllMatchingPosts();
    }

    @PostMapping("/api/v1/matchingposts") // 매칭 글 등록
    public ResponseEntity<Long> postMatchingPost(@RequestBody CreateMatchingPostRequest dto){
        log.info("Request to post matchingpost");
        Member author = memberService.findOne(dto.getAuthorId());
        MatchingPost newMatchingPost = MatchingPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .deadline(dto.getDeadline())
                .dong(dto.getDongType())
                .roomSize(dto.getRoomSizeType())
                .author(author)
                .build();
        Long newMatchingPostId = matchingService.saveMatchingPost(newMatchingPost);
        return new ResponseEntity<>(newMatchingPostId,HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/matchingposts/{matchingpostid}")
    public ResponseEntity<MatchingPost> getMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to get matchingpost by id-{}", matchingPostId);
        try{
            MatchingPost matchingPost = matchingService.findByIdMatchingPost(matchingPostId);
            return new ResponseEntity<>(matchingPost, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/v1/matchingposts/{matchingpostid}")
    public ResponseEntity<Long> deleteMatchingPost(@PathVariable("matchingpostid") Long matchingPostId){
        log.info("Request to delete matchingpost by id-{}", matchingPostId);
        try{
            Long deletedMatchingPostId = matchingService.removeMatchingPost(matchingPostId);
            return new ResponseEntity<>(deletedMatchingPostId, HttpStatus.NO_CONTENT);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
