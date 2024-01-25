package com.likelion.oegaein.dto.matching.matchingrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindMatchingReqData {
    private double matchingRate; // 매칭률
    private String title; // 제목
    // private Member author; // 매칭글 작성자
}
