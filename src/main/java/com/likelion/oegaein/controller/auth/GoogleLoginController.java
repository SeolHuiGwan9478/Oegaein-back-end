package com.likelion.oegaein.controller.auth;

import com.likelion.oegaein.dto.auth.GoogleInfResponse;
import com.likelion.oegaein.dto.auth.GoogleRequest;
import com.likelion.oegaein.dto.auth.GoogleResponse;
import com.likelion.oegaein.service.auth.GoogleLoginService;
import com.likelion.oegaein.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class GoogleLoginController {
    private final GoogleLoginService googleLoginService;
    @RequestMapping(value = "/api/v1/oauth2/google/login", method = RequestMethod.POST)

    public String loginUrlGoogle() {
        return googleLoginService.requestUrl();
    }

    @RequestMapping(value = "/api/v1/oauth2/google/login", method = RequestMethod.GET)
    public String loginGoogle(@RequestParam(value = "code") String authCode) {
        return googleLoginService.access(authCode);
    }
}