package com.likelion.oegaein.domain.matching;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RoomSizeType {
    Two("2인실"),
    Four("4인실");

    RoomSizeType(String value){
        this.value = value;
    }
    public final String value;

    @JsonCreator
    public RoomSizeType deserializerRoomSize(String value){
        for(RoomSizeType roomSizeType : RoomSizeType.values()){
            if(roomSizeType.getValue().equals(value)) {
                return roomSizeType;
            }
        }
        return null;
    }

    @JsonValue
    public String roomSizeSerializer(){
        return value;
    }
}
