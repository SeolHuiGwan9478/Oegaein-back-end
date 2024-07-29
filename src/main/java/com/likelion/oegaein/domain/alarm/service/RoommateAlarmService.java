package com.likelion.oegaein.domain.alarm.service;

import com.likelion.oegaein.domain.alarm.dto.roommate.DeleteRoommateAlarmResponse;
import com.likelion.oegaein.domain.alarm.dto.roommate.DeleteRoommateAlarmsResponse;
import com.likelion.oegaein.domain.alarm.dto.roommate.FindRoommateAlarmsData;
import com.likelion.oegaein.domain.alarm.dto.roommate.FindRoommateAlarmsResponse;
import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.alarm.repository.RoommateAlarmRepository;
import com.likelion.oegaein.domain.alarm.repository.query.RoommateAlarmQueryRepository;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoommateAlarmService {
    // constants
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_ROOMMATE_ALARM_ERR_MSG = "찾을 수 없는 룸메이트 알림입니다.";
    // repository
    private final RoommateAlarmRepository roommateAlarmRepository;
    private final RoommateAlarmQueryRepository roommateAlarmQueryRepository;
    private final MemberRepository memberRepository;
    // validator
    private final MemberValidator memberValidator;

    public FindRoommateAlarmsResponse findRoommateAlarms(Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        List<RoommateAlarm> roommateAlarms = roommateAlarmQueryRepository.findByMemberOrderByCreatedAtDesc(authenticatedMember.getId());
        List<FindRoommateAlarmsData> roommateAlarmsData = roommateAlarms.stream()
                .map(FindRoommateAlarmsData::toFindRoommateAlarmsData).toList();
        return FindRoommateAlarmsResponse.builder()
                .count(roommateAlarmsData.size())
                .data(roommateAlarmsData)
                .build();
    }

    @Transactional
    public DeleteRoommateAlarmResponse removeRoommateAlarm(Long roommateAlarmId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        RoommateAlarm roommateAlarm = roommateAlarmRepository.findById(roommateAlarmId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ROOMMATE_ALARM_ERR_MSG));
        memberValidator.validateIsOwnerRoommateAlarm(authenticatedMember.getId(), roommateAlarm.getMember().getId());
        roommateAlarmRepository.delete(roommateAlarm);
        return new DeleteRoommateAlarmResponse(roommateAlarmId);
    }

    @Transactional
    public DeleteRoommateAlarmsResponse removeRoommateAlarms(Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        int deletedRoommateAlarmCount = roommateAlarmQueryRepository.deleteAllByMember(authenticatedMember);
        return new DeleteRoommateAlarmsResponse(deletedRoommateAlarmCount);
    }
}