package com.likelion.oegaein.domain.member.dto.oauth;

import com.likelion.oegaein.domain.member.entity.profile.Gender;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleOauthLoginResponse implements ResponseDto {
    private Long id;
    private String email;
    private String name;
    private String photoUrl;
    private String introduction;
    private Gender gender;
    private String accessToken;
    private String refreshToken;
    private boolean profileSetUpStatus;
}
