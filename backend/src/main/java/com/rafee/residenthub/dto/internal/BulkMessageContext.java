package com.rafee.residenthub.dto.internal;

import lombok.Data;

@Data
public class BulkMessageContext {

    private ResidentMessageRecipient resident;
    private String message;
    private String sourceNumber;
    private String textCallbackUrl;
    private String voiceCallbackUrl;
    private String voiceAnswerUrl;
    private Long userId;
    private Long broadcastId;

}
