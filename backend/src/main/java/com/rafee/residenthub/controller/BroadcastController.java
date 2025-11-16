package com.rafee.residenthub.controller;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.request.BroadcastFilterRequest;
import com.rafee.residenthub.dto.request.BroadcastRequest;
import com.rafee.residenthub.dto.response.ApiResponse;
import com.rafee.residenthub.dto.response.BroadcastMessageResponse;
import com.rafee.residenthub.dto.response.BroadcastResponse;
import com.rafee.residenthub.entity.User;
import com.rafee.residenthub.exception.ResourceNotFoundException;
import com.rafee.residenthub.service.BroadcastService;
import com.rafee.residenthub.service.TextMessageService;
import com.rafee.residenthub.service.VoiceMessageService;
import com.rafee.residenthub.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/broadcast")
@Slf4j
@RequiredArgsConstructor
public class BroadcastController {

    private final BroadcastService broadcastService;
    private final TextMessageService textMessageService;
    private final VoiceMessageService voiceMessageService;

    @PostMapping("/text-message")
    public ResponseEntity<ApiResponse> sendTextBroadcast(@RequestBody @Valid BroadcastRequest broadcastRequest, @AuthenticationPrincipal User currentUser){

        log.info("sendTextBroadcast controller called");

        broadcastService.sendBroadcast(broadcastRequest, BroadcastMode.TEXT_MESSAGE, textMessageService, currentUser.getId());

        return ResponseEntity.ok().body(new ApiResponse("Text Broadcast initiated  successfully", null));
    }

    @PostMapping("/voice")
    public ResponseEntity<ApiResponse> sendVoiceBroadcast(@RequestBody @Valid BroadcastRequest broadcastRequest, @AuthenticationPrincipal User currentUser){

        log.info("sendBulkTextMessage controller called");

        broadcastService.sendBroadcast(broadcastRequest, BroadcastMode.VOICE, voiceMessageService, currentUser.getId());

        return ResponseEntity.ok().body(new ApiResponse("Voice Broadcast initiated successfully", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getBroadcasts(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(10) @Max(50) int size){

        log.info("getBroadcasts controller called");

        Page<BroadcastResponse> broadcastPage = broadcastService.getBroadcasts(page, size);

        return ResponseUtil.buildPagedResponse(broadcastPage, "Broadcasts fetched", "No broadcast found");

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchBroadcasts(@Valid @ModelAttribute BroadcastFilterRequest request){

        log.info("searchBroadcasts controller called");

        Page<BroadcastResponse> broadcastPage = broadcastService.searchBroadcasts(request);

        return ResponseUtil.buildPagedResponse(broadcastPage, "Broadcasts fetched", "No broadcast found");

    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<ApiResponse> getBroadcastMessages(@PathVariable Long id, Pageable pageable, @RequestParam(required = false) String status){

        log.info("getBroadcastMessage controller called {} - Page : {}", status , pageable.getPageNumber());

        Page<BroadcastMessageResponse> broadcastMessagesPage = broadcastService.getBroadcastMessages(id, pageable, status);

        List<BroadcastMessageResponse> broadcastMessagesList = broadcastMessagesPage.getContent();

        if (broadcastMessagesList.isEmpty() && status == null) {
            throw new ResourceNotFoundException("No messages found for the broadcast Id : " + id);
        }

        return ResponseEntity.ok().body(new ApiResponse("Broadcasts messages fetched", broadcastMessagesList, broadcastMessagesPage));

    }
}
