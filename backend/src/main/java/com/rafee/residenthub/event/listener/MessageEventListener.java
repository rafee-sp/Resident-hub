package com.rafee.residenthub.event.listener;

import com.rafee.residenthub.dto.enums.ActionType;
import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.enums.EntityType;
import com.rafee.residenthub.dto.enums.MessageStatus;
import com.rafee.residenthub.entity.Broadcast;
import com.rafee.residenthub.entity.MessageLog;
import com.rafee.residenthub.entity.Resident;
import com.rafee.residenthub.event.model.ActivityEvent;
import com.rafee.residenthub.event.model.MessageFailedEvent;
import com.rafee.residenthub.event.model.MessageSuccessEvent;
import com.rafee.residenthub.repository.MessageLogRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final MessageLogRepository messageLogRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final EntityManager entityManager;

    @Async
    @EventListener
    public void handleMessageSuccess(MessageSuccessEvent event) {

        MessageLog messageLog = new MessageLog();
        messageLog.setMessage(event.getMessageContent());
        messageLog.setMessageType(event.getBroadcastMode());
        messageLog.setResident(entityManager.getReference(Resident.class, event.getResidentId()));
        messageLog.setMessageSid(event.getMessageSid());
        messageLog.setMessageStatus(MessageStatus.QUEUED);
        if (event.getBroadcastId() != null && event.getBroadcastId() > 0) {
            messageLog.setBroadcast(entityManager.getReference(Broadcast.class, event.getBroadcastId()));
        }

        messageLog= messageLogRepository.save(messageLog);

        String messageType = event.getBroadcastMode() == BroadcastMode.TEXT_MESSAGE ? "Text" : "Voice";
        ActionType actionType = event.getBroadcastMode() == BroadcastMode.TEXT_MESSAGE ? ActionType.TEXT_MESSAGE_SEND : ActionType.VOICE_MESSAGE_SEND;
        String description = String.format("A %s message was successfully sent to Resident %s.", messageType, event.getResidentName());

        eventPublisher.publishEvent(ActivityEvent.userActivity(event.getUserId(), messageLog.getId(), EntityType.MESSAGE, actionType, description));

    }

    @Async
    @EventListener
    public void handleMessageFailed(MessageFailedEvent event) {

        MessageLog messageLog = new MessageLog();
        messageLog.setMessage(event.getMessageContent());
        messageLog.setMessageType(event.getBroadcastMode());
        messageLog.setResident(entityManager.getReference(Resident.class, event.getResidentId()));
        messageLog.setMessageSid(null);
        messageLog.setMessageStatus(MessageStatus.FAILED);
        messageLog= messageLogRepository.save(messageLog);

        String messageType = event.getBroadcastMode() == BroadcastMode.TEXT_MESSAGE ? "Text" : "Voice";
        ActionType actionType = event.getBroadcastMode() == BroadcastMode.TEXT_MESSAGE ? ActionType.TEXT_MESSAGE_SEND : ActionType.VOICE_MESSAGE_SEND;
        String description = String.format("A %s message failed to sent to Resident %s.", messageType, event.getResidentName());

        eventPublisher.publishEvent(ActivityEvent.userActivity(event.getUserId(), messageLog.getId(), EntityType.MESSAGE, actionType, description));

    }
}
