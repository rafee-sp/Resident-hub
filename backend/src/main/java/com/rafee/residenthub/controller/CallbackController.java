package com.rafee.residenthub.controller;

import com.rafee.residenthub.repository.MessageLogRepository;
import com.rafee.residenthub.service.CallbackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/callback")
@Slf4j
@RequiredArgsConstructor
public class CallbackController {

    private final CallbackService callbackService;
    private final MessageLogRepository messageLogRepository;

    @PostMapping("/message")
    public ResponseEntity<Void> handleTextStatusCallback(@RequestParam Map<String, String> paramsMap,
                                                     @RequestHeader(value = "X-Twilio-Signature", required = false) String signature,
                                                     HttpServletRequest request) {

        log.info("handleTextStatusCallback controller called");

        if (!callbackService.isValidTwilioResponse(paramsMap, signature, request)) {
            log.error("Text callback Signature validation failed");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        callbackService.handleTextMessageCallback(paramsMap);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/voice")
    public ResponseEntity<Void> handleVoiceStatusCallback(@RequestParam Map<String, String> paramsMap,
                                                     @RequestHeader(value = "X-Twilio-Signature", required = false) String signature,
                                                     HttpServletRequest request) {

        log.info("handleVoiceStatusCallback controller called");

        if (!callbackService.isValidTwilioResponse(paramsMap, signature, request)) {
            log.error("Voice callback Signature validation failed");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        callbackService.handleVoiceMessageCallback(paramsMap);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/voice/answer")
    public ResponseEntity<String> sendVoiceTwiml(@RequestParam String token) {

        log.info("Inside the sendVoiceTwiml method");

        try {

            String twiml = callbackService.generateVoiceAnswer(token);

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(twiml);

        } catch (Exception e) {
            log.error("Exception occurred at sendVoiceTwiml", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
