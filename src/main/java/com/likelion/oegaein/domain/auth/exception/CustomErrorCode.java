package com.likelion.oegaein.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    HUFS_EMAIL_ERROR("한국외국어대학교 이메일로 로그인 해주세요.");

    private final String statusMessage;
}