package com.rafee.residenthub.event.model;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class MessageSuccessEvent extends MessageEvent {

    private final String messageContent;
    private final String messageSid;
    private final Long broadcastId;

    public MessageSuccessEvent(Long residentId, String residentName, Long userId, BroadcastMode broadcastMode, String messageContent, String messageSid, Long broadcastId) {
        super(residentId, residentName, userId, broadcastMode);
        this.messageContent = messageContent;
        this.messageSid = messageSid;
        this.broadcastId = broadcastId;
    }

    public static MessageSuccessEvent handleTextMessageSuccess(Long residentId, String residentName, Long userId, String messageContent, String messageSid, Long broadcastId) {
        return new MessageSuccessEvent(residentId, residentName, userId, BroadcastMode.TEXT_MESSAGE, messageContent, messageSid, broadcastId);
    }

    public static MessageSuccessEvent handleVoiceMessageSuccess(Long residentId, String residentName, Long userId, String messageContent, String messageSid, Long broadcastId) {
        return new MessageSuccessEvent(residentId, residentName, userId, BroadcastMode.VOICE, messageContent, messageSid, broadcastId);
    }
}
