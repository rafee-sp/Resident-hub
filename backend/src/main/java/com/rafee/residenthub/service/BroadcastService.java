package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.internal.BroadcastSummaryDTO;
import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.request.BroadcastFilterRequest;
import com.rafee.residenthub.dto.request.BroadcastRequest;
import com.rafee.residenthub.dto.response.BroadcastMessageResponse;
import com.rafee.residenthub.dto.response.BroadcastResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BroadcastService {

    void sendBroadcast(BroadcastRequest request, BroadcastMode broadcastMode, BulkMessageHandler bulkMessageHandler, Long userId);

    Page<BroadcastResponse> getBroadcasts(int page, int size);

    Long getBroadcastCountThisMonth();

    List<BroadcastSummaryDTO> getLast5Broadcasts();

    Page<BroadcastResponse> searchBroadcasts(BroadcastFilterRequest request);

    Page<BroadcastMessageResponse> getBroadcastMessages(Long broadcastId, Pageable pageable, String status);
}
