package com.rafee.residenthub.controller;

import com.rafee.residenthub.dto.enums.CommunicationType;
import com.rafee.residenthub.dto.request.AddTemplateRequest;
import com.rafee.residenthub.dto.response.ApiResponse;
import com.rafee.residenthub.dto.response.MetaDataResponse;
import com.rafee.residenthub.entity.CommunicationTemplates;
import com.rafee.residenthub.service.MetaDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meta")
@Slf4j
@AllArgsConstructor
public class MetaDataController {

    private final MetaDataService metaDataService;

    @GetMapping
    public ResponseEntity<ApiResponse> getResidentFormMetadata(){

        log.info("getResidentFormMetadata called");

        MetaDataResponse metadata = metaDataService.getMetadata();

        return ResponseEntity.ok().body(new ApiResponse("Form Metadata fetched", metadata));
    }

    @PostMapping("/communication-templates/add")
    public ResponseEntity<ApiResponse> addCommunicationTemplate(@RequestBody @Valid AddTemplateRequest request){

        log.info("addCommunicationTemplate called");

        CommunicationTemplates template = metaDataService.addCommunicationTemplate(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Template created", template));
    }

    @GetMapping("/communication-templates")
    public ResponseEntity<ApiResponse> getCommunicationTemplatest(@RequestParam CommunicationType communicationType){

        log.info("getCommunicationTemplates called");

        List<CommunicationTemplates> communicationTemplates = metaDataService.getCommunicationTemplates(communicationType);

        if(communicationTemplates == null || communicationTemplates.isEmpty()){
            return ResponseEntity.ok().body(new ApiResponse("No Templates found", null));
        }

        return ResponseEntity.ok().body(new ApiResponse("Communication Templates fetched", communicationTemplates));
    }

}
