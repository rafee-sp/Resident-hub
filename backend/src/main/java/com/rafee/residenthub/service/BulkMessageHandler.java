package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.internal.BulkMessageContext;
import com.rafee.residenthub.dto.internal.ResidentMessageRecipient;

public interface BulkMessageHandler {

    void sendBulkMessage(ResidentMessageRecipient resident, BulkMessageContext bulkMessageContext);
}
