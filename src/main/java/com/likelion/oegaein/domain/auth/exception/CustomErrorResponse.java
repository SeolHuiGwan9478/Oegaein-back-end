package com.likelion.oegaein.domain.auth.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomErrorResponse{
    private CustomErrorCode status;
    private String statusMessage;
}