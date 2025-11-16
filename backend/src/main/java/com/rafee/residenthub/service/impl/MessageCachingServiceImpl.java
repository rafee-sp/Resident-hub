package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.service.MessageCachingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageCachingServiceImpl implements MessageCachingService {

    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.expiration.message-token}")
    private long expirySeconds;

    private static final String MESSAGE_KEY = "message:";

    @Override
    public void putMessage(String messageId, String message) {

        try{
            redisTemplate.opsForValue().set(MESSAGE_KEY+messageId, message, expirySeconds, TimeUnit.SECONDS);
            log.debug("cached message for Id : {}", messageId);
        } catch (Exception e) {
            log.error("Failed to cache message for Id : {}", messageId, e);
        }
    }

    @Override
    public String getMessage(String messageId) {

        try{
            String message =  redisTemplate.opsForValue().get(MESSAGE_KEY+messageId);
            log.info("Retrieved cached message for Id : {}", messageId);
            return message;
        } catch (Exception e) {
            log.info("Failed to retrieve the cached message for Id : {}", messageId, e);
            return null;
        }
      }

    @Override
    @Async
    public void removeMessage(String messageId) {
        try {
            redisTemplate.delete(MESSAGE_KEY + messageId);
            log.info("cached message deleted for Id : {}", messageId);

        } catch (Exception e) {
            log.info("Failed to retrieve the cached message for Id : {}", messageId, e);
        }
    }
}
