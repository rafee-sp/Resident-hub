package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.enums.MessageStatus;
import com.rafee.residenthub.dto.response.BroadcastMessageResponse;
import com.rafee.residenthub.dto.response.ResidentMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageLogService {

    Page<BroadcastMessageResponse> getBroadcastMessagesByIdAndStatus(Long broadcastId, List<MessageStatus> statusList, Pageable pageable);

    Page<BroadcastMessageResponse> getBroadcastMessagesById(Long broadcastId, Pageable pageable);

    Page<ResidentMessageResponse> getResidentMessagesByIdAndStatus(Long residentId, List<MessageStatus> statusList, Pageable pageable);

    Page<ResidentMessageResponse> getResidentMessagesById(Long residentId, Pageable pageable);

    void updateTextMessageStatus(String messageSID, String status);

    void updateVoiceMessageStatus(String callSid, String status);
}
