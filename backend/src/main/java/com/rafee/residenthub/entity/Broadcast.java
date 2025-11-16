package com.rafee.residenthub.entity;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.enums.BroadcastStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.List;

@Table(
        name = "broadcast",
        indexes = {
            @Index(name = "idx_broadcast_mode_status_date", columnList = "broadcast_mode, status, broadcast_date_time"),
            @Index(name = "idx_broadcast_date_time", columnList = "broadcast_date_time")
        }
)
@Entity
@Data
public class Broadcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "broadcast_mode", nullable = false)
    private BroadcastMode broadcastMode;

    @Column(name = "broadcast_Type", nullable = false)
    private String broadcastType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BroadcastStatus status;

    @Column(name = "total_recipients", nullable = false)
    private Long totalRecipients;

    @Column(name = "total_sent", nullable = false)
    private Long totalSent;

    @Column(name = "total_failed",nullable = false)
    private Long totalFailed;

    @Column(name = "broadcast_date_time", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime broadcastDateTime;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    @Column(name = "building")
    private String building;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tags", columnDefinition = "json")
    private List<String> tags;
}
