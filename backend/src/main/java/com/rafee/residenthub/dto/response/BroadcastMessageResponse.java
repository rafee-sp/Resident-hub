package com.rafee.residenthub.dto.response;

import com.rafee.residenthub.dto.enums.MessageStatus;

import java.time.LocalDateTime;

public record BroadcastMessageResponse(Long id, String message, MessageStatus messageStatus, LocalDateTime createdAt, Long residentId, String residentName) {
}
