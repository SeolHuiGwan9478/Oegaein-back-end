package com.likelion.oegaein.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMemberDto {
    private String email;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
