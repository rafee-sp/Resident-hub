package com.rafee.residenthub.controller;

import com.rafee.residenthub.dto.request.BroadcastSearchRequest;
import com.rafee.residenthub.dto.request.ResidentFilterRequest;
import com.rafee.residenthub.dto.request.ResidentRequest;
import com.rafee.residenthub.dto.response.*;
import com.rafee.residenthub.entity.User;
import com.rafee.residenthub.service.ResidentService;
import com.rafee.residenthub.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/residents")
@AllArgsConstructor
@Slf4j
public class ResidentsController {

    private final ResidentService residentService;

    @GetMapping
    public ResponseEntity<ApiResponse> getResidents(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(10) @Max(50) int size) throws InterruptedException {

        log.info("getResidents called");

        Page<ResidentResponse> residentsPage = residentService.getResidents(page, size);

        return ResponseUtil.buildPagedResponse(residentsPage, "Residents fetched", "No residents found");
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addResident(@RequestBody @Valid ResidentRequest resident, @AuthenticationPrincipal User currentUser) {

        log.info("add residents called");

        ResidentResponse createdResident = residentService.addResident(resident, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Resident created", createdResident));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateResident(@PathVariable Long id, @RequestBody @Valid ResidentRequest resident, @AuthenticationPrincipal User currentUser) {

        log.info("update residents called");

        residentService.updateResident(id, resident, currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        log.info("Delete residents method called");

        residentService.deleteResident(id, currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchResidents(@Valid @ModelAttribute ResidentFilterRequest searchRequest) {

        log.info("searchResidents called");

        Page<ResidentResponse> residentsPage = residentService.searchResidents(searchRequest);

        return ResponseUtil.buildPagedResponse(residentsPage, "Residents fetched", "No residents found");
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getCountOfResidents(@Valid @ModelAttribute BroadcastSearchRequest request) {

        log.info("getCountOfResidents method called");

        Long residentsCount = residentService.getResidentsCountForBroadcast(request);

        return ResponseEntity.ok().body(new ApiResponse("Resident count fetched", residentsCount));

    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<ApiResponse> getResidentsMessages(@PathVariable Long id, Pageable pageable, @RequestParam(required = false) String status){ // move to messages

        log.info("getResidentsMessages controller called {} - page : {}", status, pageable.getPageNumber());

        Page<ResidentMessageResponse> residentMessagesPage = residentService.getResidentMessages(id, pageable, status);

        return ResponseUtil.buildPagedResponse(residentMessagesPage, "Resident messages fetched", "No messages found");

    }
}
