package com.rafee.residenthub.repository;

import com.rafee.residenthub.dto.internal.BroadcastSummaryDTO;
import com.rafee.residenthub.entity.Broadcast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BroadcastRepository extends JpaRepository<Broadcast, Long> {

    Long countByBroadcastDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<BroadcastSummaryDTO> findTop5ByOrderByBroadcastDateTimeDesc();

    Page<Broadcast> findAllByOrderByIdDesc(Pageable pageable);

    Page<Broadcast> findAll(Specification<Broadcast> broadcastSpecification, Pageable pageable);
}
