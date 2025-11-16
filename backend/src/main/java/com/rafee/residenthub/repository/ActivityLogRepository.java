package com.rafee.residenthub.repository;

import com.rafee.residenthub.dto.response.ActivityResponse;
import com.rafee.residenthub.entity.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    @Query(value = "SELECT a.id as id, a.action_type as actionType, a.created_at as createdAt, a.description as description, COALESCE(u.user_name, 'SYSTEM') as performedBy FROM activity_logs a LEFT JOIN users u ON u.id = a.user_id ORDER BY a.created_at DESC", nativeQuery = true)
    Page<ActivityResponse> getActivities(Pageable pageable);

    @Query(value = "SELECT a.id as id, a.action_type as actionType, a.created_at as createdAt, a.description as description, COALESCE(u.user_name, 'SYSTEM') as performedBy " +
            "FROM activity_logs a LEFT JOIN users u ON u.id = a.user_id " +
            "WHERE (:startDate IS NULL OR a.created_at >= :startDate) " +
            "AND (:endDate IS NULL OR a.created_at <= :endDate) " +
            "AND (:actionType IS NULL OR a.action_type = :actionType) " +
            "ORDER BY a.created_at DESC", nativeQuery = true)
    Page<ActivityResponse> getFilteredActivities(@Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate,
                                                    @Param("actionType") String actionType, Pageable pageable);

}
