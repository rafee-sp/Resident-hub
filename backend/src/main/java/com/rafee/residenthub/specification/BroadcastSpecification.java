package com.rafee.residenthub.specification;

import com.rafee.residenthub.dto.enums.BroadcastMode;
import com.rafee.residenthub.dto.enums.BroadcastStatus;
import com.rafee.residenthub.dto.request.BroadcastFilterRequest;
import com.rafee.residenthub.entity.Broadcast;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@Slf4j
public class BroadcastSpecification {

    public static Specification<Broadcast> filterSpecification(BroadcastFilterRequest request){

        log.info("filterSpecification {}", request);

        return withBroadcastMode(request.getBroadcastMode()).and(withBroadcastStaus(request.getStatus())).and(withBroadcastDate(request.getBroadcastDate()));
    }

    public static Specification<Broadcast> withBroadcastMode(String mode) {

        return (root, query, criteriaBuilder) -> {

            if (!StringUtils.hasText(mode) || mode.equalsIgnoreCase("ALL")) {

                log.debug("No broadcast mode present");
                return criteriaBuilder.conjunction();

            }
            BroadcastMode broadcastMode;
            try {
                broadcastMode = BroadcastMode.valueOf(mode);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid broadcast mode "+ mode);
            }

            return criteriaBuilder.equal(root.get("broadcastMode"), broadcastMode);

        } ;

    }

    public static Specification<Broadcast> withBroadcastStaus(String status) {

        return (root, query, criteriaBuilder) -> {

            if (!StringUtils.hasText(status) || status.equalsIgnoreCase("ALL")) {

                log.info("No broadcast status present");
                return criteriaBuilder.conjunction();

            }
            BroadcastStatus broadcastStatus;
            try {
                broadcastStatus = BroadcastStatus.valueOf(status);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid broadcast status "+ status);
            }

            return criteriaBuilder.equal(root.get("status"), broadcastStatus);

        } ;

    }

    public static Specification<Broadcast> withBroadcastDate(LocalDate broadcastDate) {

        return (root, query, criteriaBuilder) -> {

            if (broadcastDate == null) {
                log.info("No broadcast date found");
                return criteriaBuilder.conjunction();
            }

            LocalDateTime dayStart = broadcastDate.atStartOfDay();
            LocalDateTime dayEnd = broadcastDate.atTime(LocalTime.MAX);

            return criteriaBuilder.between(root.get("broadcastDateTime"), dayStart, dayEnd);

        };
    }

}
