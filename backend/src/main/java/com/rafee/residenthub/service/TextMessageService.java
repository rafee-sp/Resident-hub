package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.request.MessageRequest;

public interface TextMessageService extends BulkMessageHandler {

 void handleTextMessage(MessageRequest messageRequest) ;

}
