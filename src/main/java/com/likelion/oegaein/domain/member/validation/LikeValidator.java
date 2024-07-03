package com.likelion.oegaein.domain.member.validation;

import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.exception.LikeException;
import com.likelion.oegaein.domain.member.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeValidator {
    private final String LIKE_ERR_MSG = "이미 좋아요 한 사용자입니다";
    private final LikeRepository likeRepository;
    public void validateLiked(Member sender, Member receiver){
        boolean isLiked = likeRepository.existsBySenderAndReceiver(sender, receiver);
        if(isLiked){
            throw new LikeException(LIKE_ERR_MSG);
        }
    }
}
