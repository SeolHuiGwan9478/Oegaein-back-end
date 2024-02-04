package com.likelion.oegaein.controller.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.service.member.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/v1/members/profile")
    public void getMemberProfile() {

    }

    @PutMapping("/api/v1/members/profile")
    public void updateProfile(@RequestBody String name) {
    }
}
