package com.likelion.oegaein.controller.oauth;

import com.likelion.oegaein.dto.oauth.GoogleInfResponse;
import com.likelion.oegaein.dto.oauth.GoogleRequest;
import com.likelion.oegaein.dto.oauth.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class GoogleLoginController {
    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.pw}")
    private String googleClientPw;
    @Value("${google.datasource.redirect-uri}")
    private String redirectUri;

    @RequestMapping(value="/api/v1/oauth2/google/login", method = RequestMethod.POST)
    public String loginUrlGoogle(){
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=" + redirectUri + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }
    @RequestMapping(value="/api/v1/oauth2/google/login", method = RequestMethod.GET)
    public String loginGoogle(@RequestParam(value = "code") String authCode){
        RestTemplate restTemplate = new RestTemplate();
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientPw)
                .code(authCode)
                .redirectUri(redirectUri)
                .grantType("authorization_code").build();
        ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        String jwtToken=resultEntity.getBody().getId_token();
        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfResponse.class);
        String email=resultEntity2.getBody().getEmail();
        return email;
    }
}
