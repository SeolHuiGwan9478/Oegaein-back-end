package com.likelion.oegaein.dto.matching.matchingpost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.oegaein.domain.matching.DongType;
import com.likelion.oegaein.domain.matching.RoomSizeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UpdateMatchingPostData {
    private String title;
    private String content;
    private LocalDateTime deadline;
    private DongType dongType;
    private RoomSizeType roomSizeType;
}
