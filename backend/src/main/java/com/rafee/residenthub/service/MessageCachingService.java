package com.rafee.residenthub.service;

public interface MessageCachingService {

    void putMessage(String messageId, String message);

    String getMessage(String messageId);

    void removeMessage(String messageId);
}
