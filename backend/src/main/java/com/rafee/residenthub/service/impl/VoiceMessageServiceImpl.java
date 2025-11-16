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
import com.rafee.residenthub.service.MessageCachingService;
import com.rafee.residenthub.service.ResidentService;
import com.rafee.residenthub.service.VoiceMessageService;
import com.rafee.residenthub.security.JwtService;
import com.rafee.residenthub.util.MessageUtil;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoiceMessageServiceImpl implements VoiceMessageService {

    private final ResidentService residentService;
    private final TwilioConfig twilioConfig;
    private final ApplicationEventPublisher eventPublisher;
    private final JwtService jwtService;
    private final MessageCachingService messageCachingService;
    private final AuthenticatedUser authenticatedUser;
    private volatile RateLimiter callRateLimiter;

    @PostConstruct
    private void init() {
        callRateLimiter = RateLimiter.create(twilioConfig.getCallRateLimit());
    }

    @Override
    public void handleVoiceMessage(MessageRequest messageRequest) {

        log.debug("sendTextMessage method called {}", messageRequest);

        Resident resident = residentService.getResident(messageRequest.getResidentId());

        String callbackUrl = twilioConfig.getVoiceMessageCallbackUrl();
        String answerUrl = twilioConfig.getVoiceAnswerCallbackUrl();
        String sourceNumber = twilioConfig.getPhone();

        sendVoiceMessage(resident.getId(), resident.getResidentName(), resident.getPhoneNumber(), resident.getFlatNo(), messageRequest.getMessageContent(), sourceNumber, answerUrl , callbackUrl, authenticatedUser.getCurrentUser().getId(), null);

    }

    @Override
    public void sendBulkMessage(ResidentMessageRecipient resident, BulkMessageContext bulkMessageContext) {
        sendVoiceMessage(resident.getId(), resident.getResidentName(), resident.getPhoneNumber(), resident.getFlatNo(), bulkMessageContext.getMessage(), bulkMessageContext.getSourceNumber(), bulkMessageContext.getVoiceAnswerUrl(), bulkMessageContext.getVoiceCallbackUrl(), bulkMessageContext.getUserId(), bulkMessageContext.getBroadcastId());
    }

    private void sendVoiceMessage(Long residentId, String residentName, String residentNumber, String flatNo, String message, String sourceNumber, String answerUrl, String callbackUrl, Long userId, Long broadcastId) {

        try {

            residentNumber = MessageUtil.formatPhoneNumber(residentNumber, twilioConfig.getPhoneRegion());

            message = MessageUtil.processTemplate(message, residentName, flatNo);

            String uniqueMessageId = residentId+"_"+System.currentTimeMillis();

            messageCachingService.putMessage(uniqueMessageId, message);

            String token = jwtService.generateTwilioToken(uniqueMessageId);

            answerUrl = answerUrl+"?token="+ URLEncoder.encode(token, StandardCharsets.UTF_8);

            log.info("callbackUrl : {}", callbackUrl);
            log.info("answerUrl : {}", answerUrl);

         //   String messageSid = sendTwilioMessage(residentNumber, sourceNumber, answerUrl, callbackUrl);
               int randomDigit = 10000 + new Random().nextInt(90000);
               String messageSid =  String.valueOf(randomDigit);

            eventPublisher.publishEvent(MessageSuccessEvent.handleVoiceMessageSuccess(residentId, residentName, userId, message, messageSid, broadcastId));

        } catch (Exception e) {

            log.info("An exception occurred while sending message : {}", e.getMessage());

            eventPublisher.publishEvent(MessageFailedEvent.handleVoiceMessageFailure(residentId, residentName, userId, message, broadcastId));

            throw new MessageNotSendException("Voice Message not sent for the resident");
        }
    }

    public String sendTwilioMessage(String residentNumber, String sourceNumber, String answerUrl, String callbackUrl) throws URISyntaxException {

        callRateLimiter.acquire();

        Call call = Call.creator(new PhoneNumber(residentNumber),
                        new PhoneNumber(sourceNumber),
                        URI.create(answerUrl)
                )
                .setMethod(HttpMethod.GET)
                .setStatusCallback(new URI(callbackUrl))
                .setStatusCallbackMethod(HttpMethod.POST)
                .setStatusCallbackEvent("completed")
                .create();

        log.debug("callId : {}", call.getSid());

        if (!StringUtils.hasText(call.getSid())) {
            throw new MessageNotSendException("Voice Message not sent for the resident");
        }

        return call.getSid();
    }
}
