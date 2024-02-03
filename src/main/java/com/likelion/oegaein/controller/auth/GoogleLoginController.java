package com.likelion.oegaein.controller.auth;

import com.likelion.oegaein.service.auth.GoogleLoginService;
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
    public void loginGoogle(@RequestParam(value = "code") String authCode) {
        String email = googleLoginService.access(authCode);
        if (googleLoginService.isHufsEmail(email)) {
            log.info(email);
            googleLoginService.access(authCode);
        } else {
            log.info("Not HUFS Email");
        }
    }
}