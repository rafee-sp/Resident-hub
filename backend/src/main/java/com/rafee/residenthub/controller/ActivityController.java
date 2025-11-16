package com.rafee.residenthub.controller;

import com.rafee.residenthub.dto.request.ActivityFilterRequest;
import com.rafee.residenthub.dto.response.ActivityResponse;
import com.rafee.residenthub.dto.response.ApiResponse;
import com.rafee.residenthub.service.ActivityService;
import com.rafee.residenthub.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activity")
@Slf4j
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity<ApiResponse> getActivity(Pageable pageable){

        log.info("getActivity called with pageable: page={}, size={}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ActivityResponse> activityPage = activityService.getActivities(pageable);

        log.debug("Found {} activities ", activityPage.getNumberOfElements());

        return ResponseUtil.buildPagedResponse(activityPage, "Activities fetched successfully", "No activities found");

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchActivity(@ModelAttribute ActivityFilterRequest request){

        log.info("User performed activity search with filters: {}", request);

        Page<ActivityResponse> activityPage = activityService.searchActivities(request);

        log.debug("Found {} activities matching search criteria", activityPage.getNumberOfElements());

        return ResponseUtil.buildPagedResponse(activityPage, "Activities fetched successfully", "No activities found");
    }

}
