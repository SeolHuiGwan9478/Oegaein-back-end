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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Long> createMatchingPost(@RequestBody CreateMatchingPostRequest dto){
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
}
