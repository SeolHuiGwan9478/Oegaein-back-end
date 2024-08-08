package com.likelion.oegaein.domain.member.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateBlockRequest {
    @JsonProperty("blocked_id")
    private Long blockedId;
}