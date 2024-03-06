package com.likelion.oegaein.domain.matching.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MatchingAcceptance {
    ACCEPT("수락"),
    REJECT("거절");

    MatchingAcceptance(String value){
        this.value = value;
    }

    private final String value;

    @JsonCreator
    public MatchingAcceptance deserializeMatchingAcceptance(String value){
        for(MatchingAcceptance matchingAcceptance : MatchingAcceptance.values()){
            if(matchingAcceptance.getValue().equals(value)){
                return matchingAcceptance;
            }
        }
        return null;
    }

    @JsonValue
    public String serializeMatchingAcceptance(){
        return this.value;
    }
}
