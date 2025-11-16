package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.request.ActivityFilterRequest;
import com.rafee.residenthub.dto.response.ActivityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ActivityService {

    Page<ActivityResponse> getActivities(Pageable pageable);

    Page<ActivityResponse> searchActivities(ActivityFilterRequest request);
}
