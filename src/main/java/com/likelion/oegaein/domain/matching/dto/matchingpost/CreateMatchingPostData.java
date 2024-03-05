package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMatchingPostData {
    private String title;
    private String content;
    private LocalDate deadline;
    private DongType dongType;
    private RoomSizeType roomSizeType;

    public static CreateMatchingPostData toCreateMatchingPostData(CreateMatchingPostRequest dto){
        CreateMatchingPostData createMatchingPostData = new CreateMatchingPostData();
        createMatchingPostData.setTitle(dto.getTitle());
        createMatchingPostData.setContent(dto.getContent());
        createMatchingPostData.setDeadline(dto.getDeadline());
        createMatchingPostData.setDongType(dto.getDongType());
        createMatchingPostData.setRoomSizeType(dto.getRoomSizeType());
        return createMatchingPostData;
    }
}
