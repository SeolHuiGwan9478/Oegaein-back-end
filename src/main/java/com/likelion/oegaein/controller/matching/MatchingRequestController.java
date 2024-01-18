package com.likelion.oegaein.controller.matching;

import com.likelion.oegaein.dto.matching.CreateMatchingPostResponse;
import com.likelion.oegaein.dto.matching.CreateMatchingReqData;
import com.likelion.oegaein.dto.matching.CreateMatchingReqRequest;
import com.likelion.oegaein.dto.matching.CreateMatchingReqResponse;
import com.likelion.oegaein.service.matching.MatchingRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchingRequestController {
    private final MatchingRequestService matchingRequestService;

    @PostMapping("/api/v1/matchingrequests") // 매칭 신청 등록
    public ResponseEntity<CreateMatchingReqResponse> postMatchingRequest(@RequestBody CreateMatchingReqRequest dto){
        log.info("Request to post matching requests");
        CreateMatchingReqData convertedDto = CreateMatchingReqData.toCreateMatchingReqData(dto);
        CreateMatchingReqResponse response = matchingRequestService.createMatchingRequest(convertedDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/v1/matchingrequests/{matchingrequestid}") // 매칭 신청 취소
    public ResponseEntity<Void> deleteMatchingRequest(@PathVariable("matchingrequestid") Long matchingRequestId){
        log.info("Request to delete matching request");
        matchingRequestService.removeMatchingRequest(matchingRequestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
