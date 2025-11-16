package com.rafee.residenthub.event.listener;

import com.rafee.residenthub.entity.ActivityLog;
import com.rafee.residenthub.event.model.ActivityEvent;
import com.rafee.residenthub.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityEventListener {

    private final ActivityLogRepository activityLogRepository;

    @Async
    @EventListener
    public void handleActivity(ActivityEvent event){

        ActivityLog activityLog = new ActivityLog();
        activityLog.setActionType(event.actionType());
        activityLog.setDescription(event.description());
        activityLog.setUserId(event.userId());
        activityLog.setEntityId(event.entityId());
        activityLog.setEntityType(event.entityType());
        activityLog.setActorType(event.actorType());
        activityLogRepository.save(activityLog);

    }
}
