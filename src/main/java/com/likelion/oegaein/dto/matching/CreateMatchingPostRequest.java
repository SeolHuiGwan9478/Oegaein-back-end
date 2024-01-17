package com.likelion.oegaein.dto.matching;

import com.likelion.oegaein.domain.matching.DongType;
import com.likelion.oegaein.domain.matching.RoomSizeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMatchingPostRequest {
    private String title;
    private String content;
    private LocalDateTime deadline;
    private DongType dongType;
    private RoomSizeType roomSizeType;
}
