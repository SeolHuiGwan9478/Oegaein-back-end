package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class UpdateMatchingPostData {
    private String title;
    private String content;
    private LocalDate deadline;
    private DongType dongType;
    private RoomSizeType roomSizeType;
}
