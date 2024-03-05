package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import lombok.Data;

@Data
public class CreateMatchingReqData {
    private Long matchingPostId;

    // convert CreateMatchingReqRequest into CreateMatchingReqData
    public static CreateMatchingReqData toCreateMatchingReqData(CreateMatchingReqRequest dto){
        CreateMatchingReqData createMatchingReqData = new CreateMatchingReqData();
        createMatchingReqData.setMatchingPostId(dto.getMatchingPostId());
        return createMatchingReqData;
    }
}
