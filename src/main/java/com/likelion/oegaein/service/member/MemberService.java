package com.likelion.oegaein.service.member;

import com.likelion.oegaein.domain.member.Member;
import com.likelion.oegaein.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검사
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        Member findMember = memberRepository.findById(member.getId());
        if (findMember != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 한명 조회
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
