package com.rafee.residenthub.dto.response;

import com.rafee.residenthub.dto.enums.ActionType;

import java.time.LocalDateTime;

public interface ActivityResponse {
    Long getId();
    ActionType getActionType();
    LocalDateTime getCreatedAt();
    String getDescription();
    String getPerformedBy();
}
