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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchingRequestController {
    private final MatchingRequestService matchingRequestService;

    @PostMapping("/api/v1/matchingrequests")
    public ResponseEntity<CreateMatchingReqResponse> postMatchingRequest(@RequestBody CreateMatchingReqRequest dto){
        log.info("Request to post matching requests");
        CreateMatchingReqData convertedDto = CreateMatchingReqData.toCreateMatchingReqData(dto);
        CreateMatchingReqResponse response = matchingRequestService.createMatchingRequest(convertedDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
