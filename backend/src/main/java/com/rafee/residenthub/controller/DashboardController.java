package com.rafee.residenthub.controller;

import com.rafee.residenthub.dto.response.ApiResponse;
import com.rafee.residenthub.dto.response.DashboardSummaryDTO;
import com.rafee.residenthub.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Slf4j
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse> getSummary(){

        DashboardSummaryDTO dashboardSummary = dashboardService.getDashboardSummary();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Dashboard Summary fetched", dashboardSummary));
    }

}
