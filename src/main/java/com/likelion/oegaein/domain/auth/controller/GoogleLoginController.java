package com.likelion.oegaein.domain.auth.controller;

import com.likelion.oegaein.domain.auth.service.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class GoogleLoginController {
    private final GoogleLoginService googleLoginService;
    @PostMapping("/api/v1/oauth2/google/login")
    public String loginUrlGoogle() {
        return googleLoginService.requestUrl();
    }

    @GetMapping("/api/v1/oauth2/google/login")
    public String loginGoogle(@RequestParam(value = "code") String authCode) {
        return googleLoginService.access(authCode);
    }
}