package com.likelion.oegaein.controller.auth;

import com.likelion.oegaein.dto.auth.GoogleInfResponse;
import com.likelion.oegaein.dto.auth.GoogleRequest;
import com.likelion.oegaein.dto.auth.GoogleResponse;
import com.likelion.oegaein.service.auth.GoogleLoginService;
import com.likelion.oegaein.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class GoogleLoginController {
    private final GoogleLoginService googleLoginService;
    @RequestMapping(value = "/api/v1/oauth2/google/login", method = RequestMethod.POST)

    public String loginUrlGoogle() {
        return googleLoginService.requestUrl();
    }

    @RequestMapping(value = "/api/v1/oauth2/google/login", method = RequestMethod.GET)
    public String loginGoogle(@RequestParam(value = "code") String authCode) {
        String email = googleLoginService.access(authCode);
        if (googleLoginService.isHufsEmail(email)) {
            return email;
        } else {
            log.info("Not HUFS Email");
            return "한국외국어대학교 메일로 가입해주세요";
        }
    }
}