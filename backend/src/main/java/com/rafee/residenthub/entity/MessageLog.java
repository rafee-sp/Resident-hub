package com.rafee.residenthub.entity;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "message_log", indexes = {
        @Index(name = "idx_message_log_id_status", columnList = "id,message_status")
})
@Data
@Entity
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private BroadcastMode messageType;

    @Column(name = "sid")
    private String messageSid;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status",nullable = false)
    private MessageStatus messageStatus = MessageStatus.QUEUED;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broadcast_id")
    private Broadcast broadcast;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

}
