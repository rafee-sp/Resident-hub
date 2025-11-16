package com.rafee.residenthub.specification;

import com.rafee.residenthub.dto.request.BroadcastSearchRequest;
import com.rafee.residenthub.dto.request.ResidentFilterRequest;
import com.rafee.residenthub.entity.Resident;
import com.rafee.residenthub.entity.Tags;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ResidentSpecification {

    public static Specification<Resident> searchResidentForBroadcastSpecification(BroadcastSearchRequest request) {

        log.debug("searchResidentForBroadcastSpecification  {}", request);

        Specification<Resident> specification = withBuilding(request.getBuildingId());
        specification = specification.and(withTags(request.getTagIds()));

        return specification;
    }

    public static Specification<Resident> filterSpecification(ResidentFilterRequest request) {

        log.debug("filterSpecification {}", request);

        switch (request.getFilterType()) {

            case RESIDENT_ID:
                try {
                    return hasId(Long.parseLong(request.getFilterValue()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid Id for Resident search : " + request.getFilterValue());
                }
            case RESIDENT_NAME:
                return hasNameContaining(request.getFilterValue());
            case PHONE_NUMBER:
                return hasPhoneNumberContaining(request.getFilterValue());
            case BUILDING:
                return hasBuilding(request.getFilterValue());
            case FLAT_NO:
                return hasFlatNo(request.getFilterValue());
            default:
                throw new IllegalArgumentException("Invalid Resident search filter : " + request.getFilterValue());

        }
    }

    public static Specification<Resident> hasId(Long id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
    }

    public static Specification<Resident> hasNameContaining(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("residentName"), "%" + name + "%"));
    }

    public static Specification<Resident> hasPhoneNumberContaining(String phoneNumber) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
    }

    public static Specification<Resident> hasBuilding(String building) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("building"), building);
    }

    public static Specification<Resident> hasFlatNo(String flatNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("flatNo"), flatNo);
    }

    public static Specification<Resident> withBuilding(Integer buildingId) {

        return (root, query, criteriaBuilder) -> {

            if (buildingId == null) {
                log.info("All buildings are selected");
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("building").get("id"), buildingId);

        };

    }

    public static Specification<Resident> withTags(List<Integer> tagIds) {

        return (root, query, criteriaBuilder) -> {

            if (tagIds == null || tagIds.isEmpty()) {
                log.debug("No tags present");
                return criteriaBuilder.conjunction();
            }

            if (tagIds.size() == 1) {
                log.debug("One tag found");
                Join<Resident, Tags> tagsJoin = root.join("tags", JoinType.INNER);
                return criteriaBuilder.equal(tagsJoin.get("id"), tagIds.getFirst());
            }

            if (query == null) {
                throw new IllegalStateException("The tags specification query is null");
            }

            // Resident tagId should match the tagIds
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Resident> subRoot = subquery.from(Resident.class);
            Join<Resident, Tags> subJoin = subRoot.join("tags", JoinType.INNER);

            subquery.select(criteriaBuilder.countDistinct(subJoin.get("id")))
                    .where(
                            criteriaBuilder.equal(subRoot.get("id"), root.get("id")),
                            subJoin.get("id").in(tagIds)
                    );

            return criteriaBuilder.greaterThan(subquery, 0L);

        };

    }
}
