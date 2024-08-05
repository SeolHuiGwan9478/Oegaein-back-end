package com.likelion.oegaein.domain.alarm.repository;

import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.alarm.entity.RoommateAlarmType;
import com.likelion.oegaein.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoommateAlarmRepository extends JpaRepository<RoommateAlarm, Long> {
    List<RoommateAlarm> findByMemberAndAlarmType(Member member, RoommateAlarmType type);
}