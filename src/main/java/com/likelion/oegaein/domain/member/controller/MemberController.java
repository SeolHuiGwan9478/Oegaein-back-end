package com.likelion.oegaein.domain.member.controller;

import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.dto.UpdateProfileDto;
import com.likelion.oegaein.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/profile")
    public void getMemberProfile(@RequestHeader(value = "Authorization") String token) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findMemberByEmail(auth.getPrincipal().toString());

    }

    @PutMapping("/profile")
    public void updateMemberProfile(@RequestHeader(value = "Authorization") String token, @RequestBody UpdateProfileDto form) {

    }
}
