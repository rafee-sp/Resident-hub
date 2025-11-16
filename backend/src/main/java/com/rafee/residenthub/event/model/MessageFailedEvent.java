package com.rafee.residenthub.event.model;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class MessageFailedEvent extends MessageEvent {

    private final String messageContent;
    private final Long broadcastId;

    public MessageFailedEvent(Long residentId, String residentName, Long userId, BroadcastMode broadcastMode, String messageContent, Long broadcastId) {
        super(residentId, residentName, userId, broadcastMode);
        this.messageContent = messageContent;
        this.broadcastId = broadcastId;
    }

    public static MessageFailedEvent handleTextMessageFailure(Long residentId, String residentName, Long userId, String messageContent, Long broadcastId) {
        return new MessageFailedEvent(residentId, residentName, userId, BroadcastMode.TEXT_MESSAGE, messageContent, broadcastId);
    }

    public static MessageFailedEvent handleVoiceMessageFailure(Long residentId, String residentName, Long userId, String messageContent, Long broadcastId) {
        return new MessageFailedEvent(residentId, residentName, userId, BroadcastMode.VOICE, messageContent, broadcastId);
    }
}
