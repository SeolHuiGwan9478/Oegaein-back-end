package com.likelion.oegaein.domain.member.exception;

public class OneToOneChatException extends RuntimeException{
    public OneToOneChatException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
