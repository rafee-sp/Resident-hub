package com.rafee.residenthub.dto.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.enums.BroadcastStatus;

import java.time.LocalDateTime;

public interface BroadcastSummaryDTO {

    Long getId();
    String getTitle();
    BroadcastMode getBroadcastMode();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getBroadcastDateTime();
    BroadcastStatus getStatus();

}


