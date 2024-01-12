package com.likelion.oegaein.controller.matching;

import com.likelion.oegaein.domain.matching.MatchingPost;
import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.dto.matching.CreateMatchingPostRequest;
import com.likelion.oegaein.dto.matching.UpdateMatchingPostData;
import com.likelion.oegaein.dto.matching.UpdateMatchingPostRequest;
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
        try{
            MatchingPost matchingPost = matchingService.findByIdMatchingPost(matchingPostId);
            return new ResponseEntity<>(matchingPost, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/api/v1/matchingPosts/{matchingpostid}")
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
