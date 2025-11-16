package com.rafee.residenthub.service.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.rafee.residenthub.config.TwilioConfig;
import com.rafee.residenthub.dto.internal.BulkMessageContext;
import com.rafee.residenthub.dto.internal.ResidentMessageRecipient;
import com.rafee.residenthub.dto.request.MessageRequest;
import com.rafee.residenthub.entity.Resident;
import com.rafee.residenthub.event.model.MessageFailedEvent;
import com.rafee.residenthub.event.model.MessageSuccessEvent;
import com.rafee.residenthub.exception.MessageNotSendException;
import com.rafee.residenthub.security.AuthenticatedUser;
import com.rafee.residenthub.service.ResidentService;
import com.rafee.residenthub.service.TextMessageService;
import com.rafee.residenthub.util.MessageUtil;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class TextMessageServiceImpl implements TextMessageService {

    private final ResidentService residentService;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticatedUser authenticatedUser;
    private final TwilioConfig twilioConfig;
    private volatile RateLimiter textRateLimiter;

    @PostConstruct
    public void init() {
        textRateLimiter = RateLimiter.create(twilioConfig.getTextRateLimit());
    }

    @Override
    public void handleTextMessage(MessageRequest messageRequest) {

        log.debug("sendTextMessage method called {}", messageRequest);

        Resident resident = residentService.getResident(messageRequest.getResidentId());

        String callbackUrl = twilioConfig.getTextMessageCallbackUrl();
        String sourceNumber = twilioConfig.getPhone();

        sendTextMessage(resident.getId(), resident.getResidentName(), resident.getPhoneNumber(), resident.getFlatNo(), messageRequest.getMessageContent(), sourceNumber, callbackUrl, authenticatedUser.getCurrentUser().getId(), null);

    }

    @Override
    public void sendBulkMessage(ResidentMessageRecipient resident, BulkMessageContext bulkMessageContext) {

        sendTextMessage(resident.getId(), resident.getResidentName(), resident.getPhoneNumber(), resident.getFlatNo(), bulkMessageContext.getMessage(), bulkMessageContext.getSourceNumber(), bulkMessageContext.getTextCallbackUrl(), bulkMessageContext.getUserId(), bulkMessageContext.getBroadcastId());

    }

    private void sendTextMessage(Long residentId, String residentName, String residentNumber, String flatNo, String message, String sourceNumber, String callbackUrl, Long userId, Long broadcastId) {

        try {

            residentNumber = MessageUtil.formatPhoneNumber(residentNumber, twilioConfig.getPhoneRegion());

            message = MessageUtil.processTemplate(message, residentName, flatNo);

            //   String messageSid = sendTwilioMessage(residentNumber, message, sourceNumber, callbackUrl);
            int randomDigit = 10000 + new Random().nextInt(90000);
            String messageSid = String.valueOf(randomDigit);

            eventPublisher.publishEvent(MessageSuccessEvent.handleTextMessageSuccess(residentId, residentName, userId, message, messageSid, broadcastId));

        } catch (ApiException e) {

            log.info("An exception occurred while sending message : {}", e.getMessage());

            eventPublisher.publishEvent(MessageFailedEvent.handleTextMessageFailure(residentId, residentName, userId, message, broadcastId));

            throw new MessageNotSendException("Text Message not sent for the resident");
        }
    }

    public String sendTwilioMessage(String residentNumber, String messageContent, String sourceNumber, String callbackUrl) {

        textRateLimiter.acquire();

        Message message = Message.creator(new PhoneNumber(residentNumber),
                        new PhoneNumber(sourceNumber),
                        messageContent)
                .setStatusCallback(URI.create(callbackUrl))
                .create();

        if (!StringUtils.hasText(message.getSid())) {
            throw new MessageNotSendException("Text Message not sent for the resident");
        }

        return message.getSid();
    }
}
