package com.likelion.oegaein.domain.alarm.repository.query;

import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.member.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoommateAlarmQueryRepository {
    private final EntityManager em;
    private final JdbcTemplate jdbcTemplate;

    public int deleteAllByMember(Member member){
        String jpql = "delete from RoommateAlarm ra" +
                " where ra.member = :member";
        int deletedRoommateAlarmCount = em.createQuery(jpql)
                .setParameter("member", member)
                .executeUpdate();
        em.flush();
        em.clear();
        return deletedRoommateAlarmCount;
    }

    public List<RoommateAlarm> findByMemberOrderByCreatedAtDesc(Long memberId){
        String jpql = "select ra from RoommateAlarm ra" +
                " join fetch ra.member ram" +
                " join fetch ram.profile ramp" +
                " join fetch ra.matchingPost ramap" +
                " where ra.member.id = :memberid" +
                " order by ra.createdAt desc";
        return em.createQuery(jpql, RoommateAlarm.class)
                .setParameter("memberid", memberId)
                .getResultList();
    }

    public void bulkSaveAll(List<RoommateAlarm> roommateAlarms){
        String sql = "INSERT INTO roommate_alarm (alarm_type, matchingpost_id, member_id)" +
                " VALUES (?,?,?)";
        jdbcTemplate.batchUpdate(sql,roommateAlarms,roommateAlarms.size(),
                (PreparedStatement ps, RoommateAlarm roommateAlarm) -> {
                    ps.setString(1, roommateAlarm.getAlarmType().name());
                    ps.setLong(2, roommateAlarm.getMatchingPost().getId());
                    ps.setLong(3, roommateAlarm.getMember().getId());
                }
                );
    }
}
