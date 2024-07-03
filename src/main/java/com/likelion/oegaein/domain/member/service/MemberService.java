package com.likelion.oegaein.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.likelion.oegaein.domain.member.dto.member.*;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthLoginResponse;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthToken;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthUserInfo;
import com.likelion.oegaein.domain.member.entity.member.Block;
import com.likelion.oegaein.domain.member.entity.member.Likey;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.exception.RefreshTokenException;
import com.likelion.oegaein.domain.member.repository.BlockRepository;
import com.likelion.oegaein.domain.member.repository.LikeRepository;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.util.GoogleOauthUtil;
import com.likelion.oegaein.domain.member.util.JwtUtil;
import com.likelion.oegaein.domain.member.validation.LikeValidator;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import com.likelion.oegaein.domain.member.validation.RefreshTokenValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    // constant
    private final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private final String REFRESH_TOKEN_NOT_EXIST_ERR_MSG = "Not exist refresh token";
    private final String REFRESH_TOKEN_INVALID_ERR_MSG = "Invalid refresh token";
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    // di
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final GoogleOauthUtil googleOauthUtil;
    private final MemberValidator memberValidator;
    private final RefreshTokenValidator refreshTokenValidator;
    private final BlockRepository blockRepository;
    private final LikeRepository likeRepository;
    private final LikeValidator likeValidator;

    @Transactional
    public GoogleOauthLoginResponse googleLogin(String code) throws JsonProcessingException {
        // get data from google
        ResponseEntity<String> accessTokenResponse = googleOauthUtil.requestAccessToken(code);
        GoogleOauthToken oauthToken = googleOauthUtil.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOauthUtil.requestUserInfo(oauthToken);
        GoogleOauthUserInfo userInfo = googleOauthUtil.getUserInfo(userInfoResponse);
        // validation
        memberValidator.validateIsUnivEmailDomain(userInfo.getEmail());
        // find user or save user
        Member member = memberRepository.findByEmail(userInfo.getEmail()).orElseGet(() -> {
            Member newMember = Member.builder()
                    .email(userInfo.getEmail())
                    .photoUrl(userInfo.getPicture())
                    .profileSetUpStatus(false)
                    .build();
            memberRepository.save(newMember);
            return newMember;
        });
        // generate tokens
        String accessToken = jwtUtil.generateAccessToken(member);
        String refreshToken = jwtUtil.generateRefreshToken(member);
        // setting refresh token;
        member.renewRefreshToken(refreshToken);
        return GoogleOauthLoginResponse.builder()
                .email(member.getEmail())
                .name(member.getProfile().getName())
                .gender(member.getProfile().getGender())
                .photoUrl(member.getPhotoUrl())
                .introduction(member.getProfile().getIntroduction())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .profileSetUpStatus(member.getProfileSetUpStatus())
                .build();
    }

    @Transactional
    public CreateBlockResponse createBlockMember(Authentication authentication, CreateBlockRequest form) {
        Member blockingMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        Member blockedMember = memberRepository.findById(form.getBlockedId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + form.getBlockedId()));
        Block block = Block.builder()
                .blocking(blockingMember)
                .blocked(blockedMember)
                .build();
        blockRepository.save(block);
        return new CreateBlockResponse(block.getId());
    }

    @Transactional
    public CreateLikeResponse createLikeMember(Authentication authentication, CreateLikeRequest form) {
        Member sender  = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        Member receiver = memberRepository.findById(form.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + form.getReceiverId()));
        likeValidator.validateLiked(sender, receiver);
        Likey likey = Likey.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
        likeRepository.save(likey);
        return new CreateLikeResponse(likey.getId());
    }

    @Transactional
    public DeleteLikeResponse deleteLikeMember(Authentication authentication, DeleteLikeRequest form) {
        Member sender  = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        Member receiver = memberRepository.findById(form.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + form.getReceiverId()));
        Likey likey = likeRepository.findBySenderAndReceiver(sender, receiver)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Likey"));
        likeRepository.delete(likey);
        return new DeleteLikeResponse(likey.getId());
    }

    @Transactional
    public FindAllLikeReceiversResponse findAllLikeReceivers(Authentication authentication) {
        Member sender = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        List<Likey> likeys = likeRepository.findBySender(sender)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Reviews: " + sender.getId()));
        List<FindLikeReceiverData> receivers = likeys.stream()
                .map(likey -> likey.getReceiver().getProfile())
                .map(FindLikeReceiverData::of)
                .toList();
        return FindAllLikeReceiversResponse.builder()
                .data(receivers)
                .build();
    }

    public RenewRefreshTokenResponse renewRefreshToken(HttpServletRequest request){
        Cookie refreshTokenCookie = findRefreshTokenCookie(request);
        if(refreshTokenCookie == null){ // refreshToken cookie X
            throw new RefreshTokenException(REFRESH_TOKEN_NOT_EXIST_ERR_MSG);
        }
        String refreshToken = refreshTokenCookie.getValue();
        if(!jwtUtil.validateToken(refreshToken)){
            throw new RefreshTokenException(REFRESH_TOKEN_INVALID_ERR_MSG);
        }
        String email = jwtUtil.extractEmail(refreshToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        refreshTokenValidator.validateIsEqualToRefreshTokenInDb(refreshToken, member.getRefreshToken());
        String newAccessToken = jwtUtil.generateAccessToken(member);
        return RenewRefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .profileSetUpStatus(member.getProfileSetUpStatus())
                .build();
    }

    private Cookie findRefreshTokenCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }
}
