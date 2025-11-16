package com.rafee.residenthub.dto.response;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.enums.BroadcastStatus;

import java.time.LocalDateTime;
import java.util.List;

public record BroadcastResponse(Long id, LocalDateTime broadcastDateTime, BroadcastMode broadcastMode, String broadcastType, String title, BroadcastStatus status, Long totalRecipients, Long totalSent, Long totalFailed, String building, List<String> tags, String message) {
}

