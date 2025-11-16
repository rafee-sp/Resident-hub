package com.rafee.residenthub.controller;

import com.rafee.residenthub.dto.request.MessageRequest;
import com.rafee.residenthub.dto.response.ApiResponse;
import com.rafee.residenthub.service.MessageService;
import com.rafee.residenthub.service.TextMessageService;
import com.rafee.residenthub.service.VoiceMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message")
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final TextMessageService textMessageService;
    private final VoiceMessageService voiceMessageService;

    @PostMapping("/text-message")
    public ResponseEntity<ApiResponse> sendTextMessage(@RequestBody @Valid MessageRequest messageRequest){

        log.info("sendTextMessage controller called");

        messageService.sendTextMessage(messageRequest);

        return ResponseEntity.ok().body(new ApiResponse("Text message sent successfully", null));
    }

    @PostMapping("/voice")
    public ResponseEntity<ApiResponse> sendVoiceMessage(@RequestBody @Valid MessageRequest messageRequest){

        log.info("send voice message controller called");

        messageService.sendVoiceMessage(messageRequest);

        return ResponseEntity.ok().body(new ApiResponse("Text message sent successfully", null));
    }
}
