package com.rafee.residenthub.event.model;

import com.rafee.residenthub.dto.enums.ActionType;
import com.rafee.residenthub.dto.enums.ActorType;
import com.rafee.residenthub.dto.enums.EntityType;

public record ActivityEvent(Long userId, Long entityId, EntityType entityType, ActionType actionType,
                            String description, ActorType actorType) {

    public static ActivityEvent userActivity(Long userId, Long entityId, EntityType entityType, ActionType actionType, String description) {
        return new ActivityEvent(userId, entityId, entityType, actionType, description, ActorType.USER);
    }

    public static ActivityEvent systemActivity(Long entityId, EntityType entityType, ActionType actionType, String description) {
        return new ActivityEvent(null, entityId, entityType, actionType, description, ActorType.SYSTEM);
    }

}
