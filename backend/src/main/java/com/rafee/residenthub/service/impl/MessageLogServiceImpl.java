package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.enums.ActionType;
import com.rafee.residenthub.dto.enums.EntityType;
import com.rafee.residenthub.dto.enums.MessageStatus;
import com.rafee.residenthub.dto.response.BroadcastMessageResponse;
import com.rafee.residenthub.dto.response.ResidentMessageResponse;
import com.rafee.residenthub.entity.MessageLog;
import com.rafee.residenthub.event.model.ActivityEvent;
import com.rafee.residenthub.exception.ResourceNotFoundException;
import com.rafee.residenthub.repository.MessageLogRepository;
import com.rafee.residenthub.service.MessageLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageLogServiceImpl implements MessageLogService {

    private final MessageLogRepository messageLogRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Page<BroadcastMessageResponse> getBroadcastMessagesByIdAndStatus(Long broadcastId, List<MessageStatus> statusList, Pageable pageable) {
        return messageLogRepository.getMessagesByBroadcastIdAndStatus(broadcastId, statusList, pageable);
    }

    @Override
    public Page<BroadcastMessageResponse> getBroadcastMessagesById(Long broadcastId, Pageable pageable) {
        return messageLogRepository.getMessagesByBroadcastId(broadcastId, pageable);
    }

    @Override
    public Page<ResidentMessageResponse> getResidentMessagesByIdAndStatus(Long residentId, List<MessageStatus> statusList, Pageable pageable) {
        return messageLogRepository.getMessagesByResidentIdAndStatus(residentId, statusList, pageable);
    }

    @Override
    public Page<ResidentMessageResponse> getResidentMessagesById(Long residentId, Pageable pageable) {
        return messageLogRepository.getMessagesByResidentId(residentId, pageable);
    }

    @Override
    public void updateTextMessageStatus(String messageSID, String status) {

        MessageLog message = messageLogRepository.findByMessageSid(messageSID).orElseThrow(() -> new ResourceNotFoundException("Text Message Not found "+ messageSID));

        MessageStatus messageStatus = MessageStatus.valueOf(status.toUpperCase());

        if (message.getMessageStatus().equals(messageStatus)) {
            log.error("Twilio sends duplicate status for message Id : {}, status : {}", message.getId(), status);
            return;
        }

        if (messageStatus == MessageStatus.FAILED || messageStatus == MessageStatus.UNDELIVERED) {
            message.setMessageStatus(MessageStatus.FAILED);
        } else {
            message.setMessageStatus(messageStatus);
        }

        messageLogRepository.save(message);

        String description = "The Text message status was updated to " + status;

        log.info(description);

        eventPublisher.publishEvent(ActivityEvent.systemActivity(message.getId(), EntityType.MESSAGE, ActionType.TEXT_MESSAGE_CALLBACK, description));

    }

    @Override
    public void updateVoiceMessageStatus(String callSid, String status) {

        MessageLog message = messageLogRepository.findByMessageSid(callSid).orElseThrow(() -> new ResourceNotFoundException("Voice Message Not found "+ callSid));

        MessageStatus messageStatus = MessageStatus.getVoiceMessageStatus(status);

        if (message.getMessageStatus().equals(messageStatus)) {
            log.error("Twilio sends duplicate status for voice message Id : {}, status : {}", message.getId(), status);
            return;
        }

        message.setMessageStatus(messageStatus);
        messageLogRepository.save(message);

        String description = "The voice message status was updated to " + status;

        log.info(description);

        eventPublisher.publishEvent(ActivityEvent.systemActivity(message.getId(), EntityType.MESSAGE, ActionType.VOICE_MESSAGE_CALLBACK, description));

    }

}
