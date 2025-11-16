package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.enums.ActionType;
import com.rafee.residenthub.dto.request.ActivityFilterRequest;
import com.rafee.residenthub.dto.response.ActivityResponse;
import com.rafee.residenthub.repository.ActivityLogRepository;
import com.rafee.residenthub.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityLogRepository activityLogRepository;

    @Override
    public Page<ActivityResponse> getActivities(Pageable pageable) {

        log.info("getActivities service called");

        return activityLogRepository.getActivities(pageable); // Remove system activity

    }

    @Override
    public Page<ActivityResponse> searchActivities(ActivityFilterRequest request) {

        log.info("searchActivities service called for {} - {} - {} - {}", request.getActionType(), request.getActivityDate(), request.getPage(), request.getSize());

        String actionType = null;
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if(StringUtils.hasText(request.getActionType())) {

            try {
               ActionType.valueOf(request.getActionType());
               actionType = request.getActionType();
            } catch (Exception e) {
                throw new IllegalArgumentException("Activity Filter sent invalid Action type : " + request.getActionType());
            }
        }

        if (request.getActivityDate() != null ){
            startDate = request.getActivityDate().atStartOfDay();
            endDate = request.getActivityDate().atTime(LocalTime.MAX);
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        return activityLogRepository.getFilteredActivities(startDate, endDate, actionType, pageable);
    }
}
