package com.likelion.oegaein.dto.matching.matchingpost;

import com.likelion.oegaein.domain.matching.DongType;
import com.likelion.oegaein.domain.matching.RoomSizeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMatchingPostData {
    private String title;
    private String content;
    private LocalDateTime deadline;
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
