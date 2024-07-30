package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SleepingHabit {
    SNORER("코골이형"),
    BRUXISM("이갈이형"),
    TALKER("잠꼬대형"),
    SILENT("무소음형");

    private final String value;

    @JsonCreator
    public static SleepingHabit deserializer(String value) {
        for(SleepingHabit sleepingHabit : SleepingHabit.values()){
            if(sleepingHabit.getValue().equals(value)) {
                return sleepingHabit;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
