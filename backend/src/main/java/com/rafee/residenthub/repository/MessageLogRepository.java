package com.rafee.residenthub.repository;

import com.rafee.residenthub.dto.enums.MessageStatus;
import com.rafee.residenthub.dto.response.BroadcastMessageResponse;
import com.rafee.residenthub.dto.response.ResidentMessageResponse;
import com.rafee.residenthub.entity.MessageLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {


    Optional<MessageLog> findByMessageSid(String messageId);

    @Query("SELECT new com.rafee.residenthub.dto.response.BroadcastMessageResponse(m.id, m.message, m.messageStatus, m.createdAt, m.resident.id, m.resident.residentName) FROM MessageLog m WHERE m.broadcast.id = :id")
    Page<BroadcastMessageResponse> getMessagesByBroadcastId(@Param("id") Long id, Pageable pageable);

    @Query("SELECT new com.rafee.residenthub.dto.response.BroadcastMessageResponse(m.id, m.message, m.messageStatus, m.createdAt, m.resident.id, m.resident.residentName) FROM MessageLog m WHERE m.broadcast.id = :id AND m.messageStatus IN (:failedStatuses)")
    Page<BroadcastMessageResponse> getMessagesByBroadcastIdAndStatus(@Param("id")Long id,@Param("failedStatuses") List<MessageStatus> failedStatuses, Pageable pageable);

    @Query("SELECT new com.rafee.residenthub.dto.response.ResidentMessageResponse(m.id, m.message, m.messageStatus, m.createdAt, m.messageType) FROM MessageLog m WHERE m.resident.id = :id ORDER BY m.createdAt DESC")
    Page<ResidentMessageResponse> getMessagesByResidentId(Long id, Pageable pageable);

    @Query("SELECT new com.rafee.residenthub.dto.response.ResidentMessageResponse(m.id, m.message, m.messageStatus, m.createdAt, m.messageType) FROM MessageLog m WHERE m.resident.id = :id AND m.messageStatus IN (:failedStatuses) ORDER BY m.createdAt DESC")
    Page<ResidentMessageResponse> getMessagesByResidentIdAndStatus(Long id, List<MessageStatus> failedStatuses, Pageable pageable);
}
