package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}