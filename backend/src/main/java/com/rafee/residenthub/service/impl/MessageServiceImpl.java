package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.request.MessageRequest;
import com.rafee.residenthub.service.MessageService;
import com.rafee.residenthub.service.TextMessageService;
import com.rafee.residenthub.service.VoiceMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final TextMessageService textMessageService;
    private final VoiceMessageService voiceMessageService;

    @Override
    public void sendTextMessage(MessageRequest messageRequest) {
        textMessageService.handleTextMessage(messageRequest);
    }

    @Override
    public void sendVoiceMessage(MessageRequest messageRequest) {
        voiceMessageService.handleVoiceMessage(messageRequest);
    }
}
