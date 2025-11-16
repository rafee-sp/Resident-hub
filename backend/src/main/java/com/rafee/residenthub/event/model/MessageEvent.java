package com.rafee.residenthub.event.model;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class MessageEvent {

    private final Long residentId;
    private final String residentName;
    private final Long userId;
    private final BroadcastMode broadcastMode;
}