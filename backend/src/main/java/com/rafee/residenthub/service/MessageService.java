package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.request.MessageRequest;

public interface MessageService {

    void sendTextMessage(MessageRequest messageRequest);

    void sendVoiceMessage(MessageRequest messageRequest);

}
