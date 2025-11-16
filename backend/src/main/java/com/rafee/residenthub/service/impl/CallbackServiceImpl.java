package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.config.TwilioConfig;
import com.rafee.residenthub.dto.enums.MessageStatus;
import com.rafee.residenthub.exception.ResourceNotFoundException;
import com.rafee.residenthub.exception.UnAuthorizedException;
import com.rafee.residenthub.service.CallbackService;
import com.rafee.residenthub.service.MessageCachingService;
import com.rafee.residenthub.security.JwtService;
import com.rafee.residenthub.service.MessageLogService;
import com.twilio.security.RequestValidator;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallbackServiceImpl implements CallbackService {

    private final TwilioConfig twilioConfig;
    private final JwtService jwtService;
    private final MessageCachingService messageCachingService;
    private final MessageLogService messageLogService;

    @Override
    public boolean isValidTwilioResponse(Map<String, String> paramsMap, String signature, HttpServletRequest request) {
        log.info("isValidTwilioResponse called");

        try {

            if (signature == null || signature.isEmpty()) return false;

            String scheme = request.getHeader("X-Forwarded-Proto");
            if (scheme == null) {
                scheme = request.getScheme();
            }
            String url = scheme + "://" + request.getServerName() + request.getRequestURI();
            log.info("before url : {}", url);
            if (request.getQueryString() != null) {
                url += "?" + request.getQueryString();
            }

            RequestValidator validator = new RequestValidator(twilioConfig.getAuthId());

            return validator.validate(url, paramsMap, signature);

        } catch (Exception e) {
            log.error("Error validating twilio signature", e);
            return false;
        }
    }

    @Override
    public void handleTextMessageCallback(Map<String, String> paramsMap) {

        log.info("handleTextMessageCallback called");

        if (paramsMap == null || paramsMap.isEmpty()) {
            log.error("twilio sends response with missing parameters");
            return;
        }

        String messageSID = paramsMap.get("MessageSid");
        String status = paramsMap.get("MessageStatus");

        log.debug("MessageSid {}, status {}", messageSID, status);

        if ((messageSID == null || messageSID.isBlank()) || (status == null || status.isBlank())) {
            log.error("No messageId or status present in the Text callback");
            return;
        }

        if (!MessageStatus.isValidTextMessageStatus(status)) {
            log.error("Unhandled message status : {}", status);
            return;
        }

        messageLogService.updateTextMessageStatus(messageSID, status);
    }

    @Override
    public String generateVoiceAnswer(String token) {

        log.info("generateVoiceAnswer called");

        String messageId = jwtService.validateAndExtractMessageId(token);

        if (!StringUtils.hasText(messageId)) {
            throw new UnAuthorizedException("Twilio answer token validation failed");
        }

        String message = messageCachingService.getMessage(messageId);

        if (!StringUtils.hasText(message)) {
            throw new ResourceNotFoundException("Message not found in redis " + messageId);
        }

        Say say = new Say.Builder(message).build();

        VoiceResponse voiceResponse = new VoiceResponse.Builder()
                .say(say)
                .build();

        log.debug(voiceResponse.toXml());
        messageCachingService.removeMessage(messageId);
        return voiceResponse.toXml();
    }

    @Override
    public void handleVoiceMessageCallback(Map<String, String> paramsMap) {
        log.info("handleVoiceMessageCallback called");

        if (paramsMap == null || paramsMap.isEmpty()) {
            log.error("Twilio sends response with missing parameters");
            return;
        }

        String callSid = paramsMap.get("CallSid");
        String callStatus = paramsMap.get("CallStatus");

        log.debug("callSid {}, callStatus {}", callSid, callStatus);

        if (!StringUtils.hasText(callSid) || !StringUtils.hasText(callStatus)) {
            log.error("No callSid or status present in the voice callback");
            return;
        }

        messageLogService.updateVoiceMessageStatus(callSid, callStatus);
    }
}
