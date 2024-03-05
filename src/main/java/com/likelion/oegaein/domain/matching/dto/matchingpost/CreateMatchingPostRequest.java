package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMatchingPostRequest {
    private String title;
    private String content;
    private LocalDate deadline;
    private DongType dongType;
    private RoomSizeType roomSizeType;
}
