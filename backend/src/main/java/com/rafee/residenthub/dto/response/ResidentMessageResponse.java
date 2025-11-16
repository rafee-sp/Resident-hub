package com.rafee.residenthub.dto.response;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.enums.MessageStatus;

import java.time.LocalDateTime;

public record ResidentMessageResponse(Long id, String message, MessageStatus messageStatus, LocalDateTime createdAt, BroadcastMode broadcastMode) {
}
