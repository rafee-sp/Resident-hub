package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.enums.CommunicationType;
import com.rafee.residenthub.dto.request.AddTemplateRequest;
import com.rafee.residenthub.dto.response.MetaDataResponse;
import com.rafee.residenthub.entity.CommunicationTemplates;
import jakarta.validation.Valid;

import java.util.List;

public interface MetaDataService {

    MetaDataResponse getMetadata();

    CommunicationTemplates addCommunicationTemplate(@Valid AddTemplateRequest request);

    List<CommunicationTemplates> getCommunicationTemplates(CommunicationType communicationType);
}
