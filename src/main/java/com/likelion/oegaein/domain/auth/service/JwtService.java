package com.likelion.oegaein.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JwtService {
    private final GoogleLoginService googleLoginService;
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization")).filter(
                accessToken -> accessToken.startsWith("Bearer ")
        ).map(accessToken -> accessToken.replace("Bearer ", ""));

        // Authorization: Bearer {AccessToken}
    }

//    public Optional<String> extractEmail(String accessToken) {
//        String email = googleLoginService.requestGoogleInfo(accessToken).getKakaoId();
//        return Optional.ofNullable(email);
//    }
}
