package com.likelion.oegaein.service.auth;

import com.likelion.oegaein.dto.auth.GoogleInfResponse;
import com.likelion.oegaein.dto.auth.GoogleRequest;
import com.likelion.oegaein.dto.auth.GoogleResponse;
import com.likelion.oegaein.service.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GoogleLoginService {
    private final MemberService memberService;
    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.pw}")
    private String googleClientPw;
    @Value("${google.datasource.redirect-uri}")
    private String redirectUri;
    public String requestUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=" + redirectUri + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
    }

    public String access(String authCode) {
        RestTemplate restTemplate = new RestTemplate();
        // 요청
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientPw)
                .code(authCode)
                .redirectUri(redirectUri)
                .grantType("authorization_code").build();

        ResponseEntity<GoogleResponse> googleResponseEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        String jwtToken= Objects.requireNonNull(googleResponseEntity.getBody()).getId_token();
        log.info("jwtToken: " + jwtToken);

        Map<String, String> map = new HashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfResponse> googleInfResponseEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfResponse.class);
        String email = Objects.requireNonNull(googleInfResponseEntity.getBody()).getEmail();
        String refreshToken = Objects.requireNonNull(googleResponseEntity.getBody()).getRefresh_token();
        memberService.join(email, refreshToken);
        return email;
    }
}
