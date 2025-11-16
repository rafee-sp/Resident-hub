package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.config.TwilioConfig;
import com.rafee.residenthub.dto.internal.BroadcastSummaryDTO;
import com.rafee.residenthub.dto.internal.BulkMessageContext;
import com.rafee.residenthub.dto.internal.ResidentMessageRecipient;
import com.rafee.residenthub.dto.enums.*;
import com.rafee.residenthub.dto.request.BroadcastFilterRequest;
import com.rafee.residenthub.dto.request.BroadcastRequest;
import com.rafee.residenthub.dto.request.BroadcastSearchRequest;
import com.rafee.residenthub.dto.response.BroadcastMessageResponse;
import com.rafee.residenthub.dto.response.BroadcastResponse;
import com.rafee.residenthub.entity.Broadcast;
import com.rafee.residenthub.event.model.ActivityEvent;
import com.rafee.residenthub.repository.BroadcastRepository;
import com.rafee.residenthub.repository.TagsRepository;
import com.rafee.residenthub.mapper.BroadcastMapper;
import com.rafee.residenthub.service.*;
import com.rafee.residenthub.specification.BroadcastSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {

    private final BroadcastRepository broadcastRepository;
    private final ResidentService residentService;
    private final ApplicationEventPublisher eventPublisher;
    private final BuildingService buildingService;
    private final TagsRepository tagsRepository;
    private final BroadcastMapper broadcastMapper;
    private final MessageLogService messageLogService;
    private final TwilioConfig twilioConfig;
    private final Executor asyncExecutor;

    @Override
    public void sendBroadcast(BroadcastRequest request, BroadcastMode broadcastMode, BulkMessageHandler bulkMessageHandler, Long userId) {

        Broadcast broadcast = createBroadcast(request, broadcastMode, userId);

        CompletableFuture.runAsync(() -> handleBroadcastMessage(broadcast, request, bulkMessageHandler, userId), asyncExecutor);

    }

    @Override
    public Page<BroadcastResponse> getBroadcasts(int page, int size) {

        log.info("getBroadcasts called page {} - {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<Broadcast> broadcastPage = broadcastRepository.findAllByOrderByIdDesc(pageable);

        return broadcastPage.map(broadcastMapper::toDTO);
    }

    @Override
    public Long getBroadcastCountThisMonth() {

        log.info("getBroadcastCountThisMonth called");

        LocalDateTime monthStart = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime monthEnd = monthStart.plusMonths(1);

        log.info("monthStart : {}, monthEnd : {} ", monthStart, monthEnd);

        return broadcastRepository.countByBroadcastDateTimeBetween(monthStart, monthEnd);
    }

    @Override
    public List<BroadcastSummaryDTO> getLast5Broadcasts() {

        log.info("getLast5Broadcasts called");

        return broadcastRepository.findTop5ByOrderByBroadcastDateTimeDesc();
    }

    @Override
    public Page<BroadcastResponse> searchBroadcasts(BroadcastFilterRequest request) {

        log.info("searchBroadcasts called {}", request);

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Broadcast> broadcastPage = broadcastRepository.findAll(BroadcastSpecification.filterSpecification(request), pageable);

        return broadcastPage.map(broadcastMapper::toDTO);

    }

    @Override
    public Page<BroadcastMessageResponse> getBroadcastMessages(Long broadcastId, Pageable pageable, String status) {

        log.info("getBroadcastMessages service called broadcastId {}, status {}, page {} - {}", broadcastId, status, pageable.getPageNumber(), pageable.getPageSize());

        if(StringUtils.hasText(status) && status.equalsIgnoreCase("FAILED")){

            List<MessageStatus> failedStatuses = List.of(MessageStatus.FAILED, MessageStatus.NO_ANSWER);
            return messageLogService.getBroadcastMessagesByIdAndStatus(broadcastId, failedStatuses, pageable);
        }

        return messageLogService.getBroadcastMessagesById(broadcastId, pageable);
    }

    public void handleBroadcastMessage(Broadcast broadcast, BroadcastRequest request, BulkMessageHandler bulkMessageHandler, Long userId) {

        log.info("handleBroadcastMessage called");

        List<ResidentMessageRecipient> residents = residentService.searchResidentsForMessaging(new BroadcastSearchRequest(request.getBuildingId(), request.getTagIds()));

        AtomicLong total = new AtomicLong(residents.size());
        AtomicLong success = new AtomicLong(0);
        AtomicLong failed = new AtomicLong(0);

        BulkMessageContext bulkMessageContext = getBulkMessageContext(request, userId, broadcast.getId());

        List<List<ResidentMessageRecipient>> residentBatches = convertToBatch(residents, 25);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (List<ResidentMessageRecipient> batch : residentBatches) {

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                processBatch(batch, bulkMessageContext, success, failed, bulkMessageHandler);
            }, asyncExecutor);

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                .thenRun(() -> {

                    log.info("Bulk SMS completed. Total: {}, Success: {}, Failed: {}",
                            total.get(), success.get(), failed.get());
                    if (success.get() == 0) {
                        broadcast.setStatus(BroadcastStatus.FAILED);
                    } else {
                        broadcast.setStatus(BroadcastStatus.COMPLETED);
                    }
                    broadcast.setTotalRecipients(total.get());
                    broadcast.setTotalSent(success.get());
                    broadcast.setTotalFailed(failed.get());
                    broadcastRepository.save(broadcast);

                })
                .exceptionally(e -> {
                    broadcast.setStatus(BroadcastStatus.FAILED);
                    broadcastRepository.save(broadcast);
                    return null;
                });

    }

    private Broadcast createBroadcast(BroadcastRequest request, BroadcastMode broadcastMode, Long userId) {

        Broadcast broadcast = new Broadcast();
        broadcast.setMessage(request.getMessageContent());
        broadcast.setStatus(BroadcastStatus.IN_PROGRESS);
        broadcast.setBroadcastMode(broadcastMode);
        broadcast.setTitle(request.getTitle());
        broadcast.setBroadcastType(request.getBroadcastType());
        broadcast.setTotalRecipients(0L);
        broadcast.setTotalSent(0L);
        broadcast.setTotalFailed(0L);
        broadcast.setBroadcastDateTime(LocalDateTime.now());

        if (request.getBuildingId() == null) {
            broadcast.setBuilding("ALL");
        } else {
            String buildingName = buildingService.getBuildingName(request.getBuildingId());
            broadcast.setBuilding(buildingName);
        }

        if(!request.getTagIds().isEmpty()) {
            List<String> tags = tagsRepository.findTagsNamesById(request.getTagIds());
            broadcast.setTags(tags);
        }

        broadcast = broadcastRepository.save(broadcast);

        String description = String.format("An %s broadcast was initiated for %s", broadcastMode, request.getBroadcastType());
        eventPublisher.publishEvent(ActivityEvent.userActivity(userId, broadcast.getId(), EntityType.BROADCAST, ActionType.BROADCAST, description));

        return broadcast;

    }

    private BulkMessageContext getBulkMessageContext(BroadcastRequest request, Long userId, Long broadcastId) {
        BulkMessageContext bulkMessageContext = new BulkMessageContext();
        bulkMessageContext.setMessage(request.getMessageContent());
        bulkMessageContext.setTextCallbackUrl(twilioConfig.getTextMessageCallbackUrl());
        bulkMessageContext.setSourceNumber(twilioConfig.getPhone());
        bulkMessageContext.setVoiceCallbackUrl(twilioConfig.getVoiceMessageCallbackUrl());
        bulkMessageContext.setVoiceAnswerUrl(twilioConfig.getVoiceAnswerCallbackUrl());
        bulkMessageContext.setUserId(userId);
        bulkMessageContext.setBroadcastId(broadcastId);
        return bulkMessageContext;
    }

    private void processBatch(List<ResidentMessageRecipient> batch, BulkMessageContext bulkMessageContext, AtomicLong success, AtomicLong failed, BulkMessageHandler bulkMessageHandler) {

        for (ResidentMessageRecipient resident : batch) {
            try {
                bulkMessageHandler.sendBulkMessage(resident, bulkMessageContext);
                success.incrementAndGet();
            } catch (Exception e) {
                log.warn("Error sending message to {}: {}", resident.getId(), e.getMessage());
                failed.incrementAndGet();
            }
        }
    }

    private <T> List<List<T>> convertToBatch(List<T> list, int batchSize) {

        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            int end = Math.min(i + batchSize, list.size());
            batches.add(list.subList(i, end));
        }

        return batches;

    }

}
