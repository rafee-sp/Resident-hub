package com.rafee.residenthub.dto.response;


import com.rafee.residenthub.dto.internal.BroadcastSummaryDTO;
import lombok.Data;

import java.util.List;

@Data
public class DashboardSummaryDTO {

    Long residentsCount;
    Long broadcastCountThisMonth;
    List<BroadcastSummaryDTO> recentBroadcasts;

}
