package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.internal.ResidentMessageRecipient;
import com.rafee.residenthub.dto.request.BroadcastSearchRequest;
import com.rafee.residenthub.dto.request.ResidentFilterRequest;
import com.rafee.residenthub.dto.request.ResidentRequest;
import com.rafee.residenthub.dto.response.ResidentMessageResponse;
import com.rafee.residenthub.dto.response.ResidentResponse;
import com.rafee.residenthub.entity.Resident;
import com.rafee.residenthub.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResidentService {

    ResidentResponse addResident(ResidentRequest resident, User currentUser);

    void updateResident(Long id, ResidentRequest residentRequestDTO, User currentUser);

    void deleteResident(Long id, User currentUser);

    Resident getResident(Long residentId);

    Page<ResidentResponse> getResidents(int page, int size);

    Long getResidentsCount();

    Page<ResidentResponse> searchResidents(ResidentFilterRequest searchRequest);

    Long getResidentsCountForBroadcast(BroadcastSearchRequest request);

    List<ResidentMessageRecipient> searchResidentsForMessaging(@Valid BroadcastSearchRequest request);

    Page<ResidentMessageResponse> getResidentMessages(Long id, Pageable pageable, String status);

}
