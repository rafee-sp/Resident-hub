package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.internal.ResidentMessageRecipient;
import com.rafee.residenthub.dto.enums.ActionType;
import com.rafee.residenthub.dto.enums.EntityType;
import com.rafee.residenthub.dto.enums.MessageStatus;
import com.rafee.residenthub.dto.request.BroadcastSearchRequest;
import com.rafee.residenthub.dto.request.ResidentFilterRequest;
import com.rafee.residenthub.dto.request.ResidentRequest;
import com.rafee.residenthub.dto.response.ResidentMessageResponse;
import com.rafee.residenthub.dto.response.ResidentResponse;
import com.rafee.residenthub.entity.Building;
import com.rafee.residenthub.entity.Resident;
import com.rafee.residenthub.entity.Tags;
import com.rafee.residenthub.entity.User;
import com.rafee.residenthub.event.model.ActivityEvent;
import com.rafee.residenthub.exception.ResourceConflictException;
import com.rafee.residenthub.exception.ResourceNotFoundException;
import com.rafee.residenthub.repository.ResidentRepository;
import com.rafee.residenthub.repository.TagsRepository;
import com.rafee.residenthub.mapper.ResidentMapper;
import com.rafee.residenthub.service.BuildingService;
import com.rafee.residenthub.service.MessageLogService;
import com.rafee.residenthub.service.ResidentService;
import com.rafee.residenthub.specification.ResidentSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ResidentServiceImpl implements ResidentService {

    private final TagsRepository tagsRepository;
    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;
    private final MessageLogService messageLogService;
    private final BuildingService buildingService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public ResidentResponse addResident(ResidentRequest residentRequest, User currentUser) {

        log.info("addResident service called {}", residentRequest);

        checkDuplicatePhoneNumber(residentRequest.getPhoneNumber(), residentRequest.getFlatNo(), null);

        Building building = getBuilding(residentRequest.getBuildingId());

        Resident newResident = residentMapper.toEntity(residentRequest);

        newResident.setBuilding(building);

        if (residentRequest.getTagIds() != null && !residentRequest.getTagIds().isEmpty()) {
            Set<Tags> tagSet = new HashSet<>(tagsRepository.findAllById(residentRequest.getTagIds()));
            newResident.setTags(tagSet);
        }

        Resident createdResident = residentRepository.save(newResident);

        String description = String.format("New Resident added to system [%s]",createdResident.getResidentName());
        log.info(description);
        eventPublisher.publishEvent(ActivityEvent.userActivity(currentUser.getId(), createdResident.getId(), EntityType.RESIDENT, ActionType.RESIDENT_CREATE, description));

        return residentMapper.toDTO(createdResident);
    }

    @Transactional
    @Override
    public void updateResident(Long id, ResidentRequest residentRequest, User currentUser) {

        log.info("updateResident called for {} {}", id, residentRequest);

        Resident existingResident = getResident(id);

        checkDuplicatePhoneNumber(residentRequest.getPhoneNumber(), residentRequest.getFlatNo(), id);

        Building building = getBuilding(residentRequest.getBuildingId());

        existingResident.setResidentName(residentRequest.getResidentName());
        existingResident.setPhoneNumber(residentRequest.getPhoneNumber());
        existingResident.setBuilding(building);
        existingResident.setFlatNo(residentRequest.getFlatNo());

        if (residentRequest.getTagIds() != null && !residentRequest.getTagIds().isEmpty()) {
            Set<Tags> tagSet = new HashSet<>(tagsRepository.findAllById(residentRequest.getTagIds()));
            existingResident.setTags(tagSet);
        } else {
            existingResident.getTags().clear();
        }

        String description = String.format("Resident updated [%s]",existingResident.getResidentName());
        log.info(description);
        eventPublisher.publishEvent(ActivityEvent.userActivity(currentUser.getId(), existingResident.getId(), EntityType.RESIDENT, ActionType.RESIDENT_UPDATE, description));

        residentRepository.save(existingResident);
    }

    @Transactional
    @Override
    public void deleteResident(Long id, User currentUser) {

        log.info("deleteResident method called for {}", id);

        Resident existingResident = getResident(id);

        String residentName = existingResident.getResidentName();

        residentRepository.delete(existingResident);

        String description = String.format("Resident deleted [%s]",residentName);
        log.info(description);
        eventPublisher.publishEvent(ActivityEvent.userActivity(currentUser.getId(), existingResident.getId(), EntityType.RESIDENT, ActionType.RESIDENT_DELETE, description));

    }

    @Override
    public Resident getResident(Long id) {
        return residentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resident Not found " + id));
    }

    @Override
    public Page<ResidentResponse> getResidents(int page, int size) {

        log.info("getResidents method called page {} - {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<Resident> residentPage = residentRepository.findAllByOrderByIdDesc(pageable);

        return residentPage.map(residentMapper::toDTO);
    }

    @Override
    public Long getResidentsCount() {
        return residentRepository.getTotalResidentsCount();
    }

    @Override
    public Page<ResidentResponse> searchResidents(ResidentFilterRequest searchRequest) {

        log.info("searchResident called {}", searchRequest);

        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize());

        Page<Resident> residentPage = residentRepository.findAll(ResidentSpecification.filterSpecification(searchRequest), pageable);

        return residentPage.map(residentMapper::toDTO);

        /*
        if(searchRequest.getFilterType().equals(ResidentFilterType.RESIDENT_ID)){

           residentPage = residentRepository.findById(Long.parseLong(searchRequest.getFilterValue()), pageable);

        } else if (searchRequest.getFilterType().equals(ResidentFilterType.RESIDENT_NAME)) {

            residentPage = residentRepository.findByResidentNameContainingIgnoreCase(searchRequest.getFilterValue(), pageable);

        } else if (searchRequest.getFilterType().equals(ResidentFilterType.PHONE_NUMBER)) {

            residentPage = residentRepository.findByPhoneNumberContainingIgnoreCase(searchRequest.getFilterValue(), pageable);

        } else if (searchRequest.getFilterType().equals(ResidentFilterType.BUILDING)) {

            residentPage = residentRepository.findByBuilding(searchRequest.getFilterValue(), pageable);

        } else if (searchRequest.getFilterType().equals(ResidentFilterType.FLAT_NO)) {

       //     residentPage = residentRepository.findByFlatNo(searchRequest.getFilterValue());

        } else {
            throw new IllegalArgumentException("Filter type is invalid : "+ searchRequest.getFilterType());
        }

                List<ResidentResponseDTO> residentsList = residentMapper.toListDTO(residentPage.getContent());

        if (residentsList.isEmpty()) {
            return new ApiResponse<>(200, "No residents found", residentsList, residentPage);
        }

        return new ApiResponse<>(200, "Residents Fetched Successfully", residentsList, residentPage);

    */

    }

    @Override
    public Long getResidentsCountForBroadcast(BroadcastSearchRequest request) {

        log.info("getResidentsCountForBroadcast called");
        return residentRepository.count(ResidentSpecification.searchResidentForBroadcastSpecification(request));
    }

    @Override
    public List<ResidentMessageRecipient> searchResidentsForMessaging(BroadcastSearchRequest request) {

        log.info("searchResidentsForMessaging called {}", request);

        List<ResidentMessageRecipient> searchResults = residentRepository.
                findBy(ResidentSpecification.searchResidentForBroadcastSpecification(request),
                        specificationFluentQuery -> specificationFluentQuery
                                .as(ResidentMessageRecipient.class).all());

        if (searchResults.isEmpty()) {
            log.info("searchResidentsForMessaging is empty");
        }

        return searchResults;
    }

    @Override
    public Page<ResidentMessageResponse> getResidentMessages(Long id, Pageable pageable, String status) {

        log.info("getResidentMessages called for id {}, status {}, page {} - {}", id, status, pageable.getPageNumber(), pageable.getPageSize());

        if (StringUtils.hasText(status) && status.equalsIgnoreCase("FAILED")) {

            List<MessageStatus> failedStatuses = List.of(MessageStatus.FAILED, MessageStatus.NO_ANSWER);
            return messageLogService.getResidentMessagesByIdAndStatus(id, failedStatuses, pageable);
        }

        return messageLogService.getResidentMessagesById(id, pageable);
    }

    private Building getBuilding(Integer buildingId) {
        return buildingService.getBuilding(buildingId);
    }

    private void checkDuplicatePhoneNumber(String phoneNumber, String flatNo, Long id) {

        Optional<Resident> phoneDuplicate = residentRepository.findByPhoneNumber(phoneNumber);
        if (phoneDuplicate.isPresent() && (id == null || !phoneDuplicate.get().getId().equals(id))) {
            throw new ResourceConflictException("Resident with same phone number already exists", "DUPLICATE_PHONE");
        }

        Optional<Resident> flatDuplicate = residentRepository.findByFlatNo(flatNo);
        if (flatDuplicate.isPresent() && (id == null || !flatDuplicate.get().getId().equals(id))) {
            throw new ResourceConflictException("Resident with same Flat already exists", "DUPLICATE_FLAT");
        }

    }
}
