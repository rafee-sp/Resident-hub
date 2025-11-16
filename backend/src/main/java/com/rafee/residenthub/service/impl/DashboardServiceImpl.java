package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.internal.BroadcastSummaryDTO;
import com.rafee.residenthub.dto.response.DashboardSummaryDTO;
import com.rafee.residenthub.service.BroadcastService;
import com.rafee.residenthub.service.DashboardService;
import com.rafee.residenthub.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ResidentService residentService;
    private final BroadcastService broadcastService;

    @Override
    public DashboardSummaryDTO getDashboardSummary() {

        log.info("getDashboardSummary called");

        Long residentCount = residentService.getResidentsCount();

        Long broadcastCount = broadcastService.getBroadcastCountThisMonth();

        List<BroadcastSummaryDTO> recentBroadcasts = broadcastService.getLast5Broadcasts();

        DashboardSummaryDTO dashboardSummary = new DashboardSummaryDTO();
        dashboardSummary.setResidentsCount(residentCount);
        dashboardSummary.setBroadcastCountThisMonth(broadcastCount);
        dashboardSummary.setRecentBroadcasts(recentBroadcasts);

        log.info("residentCount {}, broadcastCount {}, recentBroadcasts {}", residentCount, broadcastCount, recentBroadcasts.size());

        return dashboardSummary;
    }


}
