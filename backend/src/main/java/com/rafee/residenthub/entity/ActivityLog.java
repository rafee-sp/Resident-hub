package com.rafee.residenthub.entity;

import com.rafee.residenthub.dto.enums.ActionType;
import com.rafee.residenthub.dto.enums.ActorType;
import com.rafee.residenthub.dto.enums.EntityType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "activity_logs",
        indexes = {@Index(name = "idx_activity_user_entity", columnList = "user_id, entity_type")
        })
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type", nullable = false)
    private ActorType actorType;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;
}
