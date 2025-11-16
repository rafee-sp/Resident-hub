package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.request.MessageRequest;

public interface VoiceMessageService extends BulkMessageHandler {

    void handleVoiceMessage(MessageRequest messageRequest) ;

}
