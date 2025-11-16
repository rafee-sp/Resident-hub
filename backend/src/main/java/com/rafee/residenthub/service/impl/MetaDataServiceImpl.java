package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.enums.CommunicationType;
import com.rafee.residenthub.dto.request.AddTemplateRequest;
import com.rafee.residenthub.dto.response.BuildingsDTO;
import com.rafee.residenthub.dto.response.MetaDataResponse;
import com.rafee.residenthub.dto.response.TagsDTO;
import com.rafee.residenthub.entity.CommunicationTemplates;
import com.rafee.residenthub.repository.CommunicationTemplateRepository;
import com.rafee.residenthub.repository.TagsRepository;
import com.rafee.residenthub.service.BuildingService;
import com.rafee.residenthub.service.MetaDataService;
import com.rafee.residenthub.mapper.TagsMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MetaDataServiceImpl implements MetaDataService {

    private final BuildingService buildingService;
    private final TagsRepository tagsRepository;
    private final CommunicationTemplateRepository communicationTemplateRepository;
    private final TagsMapper tagsMapper;

    @Override
    public MetaDataResponse getMetadata() {

        log.info("getFormMetadata called");

        List<BuildingsDTO> buildingsList = buildingService.getAllBuildings();

        List<TagsDTO> tagsList = tagsMapper.toListDTO(tagsRepository.findAll());

        return new MetaDataResponse(buildingsList, tagsList);
    }

    @Override
    public CommunicationTemplates addCommunicationTemplate(AddTemplateRequest request) {  // TODO

        log.info("addCommunicationTemplate called");

        CommunicationTemplates template = new CommunicationTemplates();
        template.setTemplate(request.getTemplate());
        template.setCommunicationType(request.getCommunicationType());

        return communicationTemplateRepository.save(template);
    }

    @Override
    public List<CommunicationTemplates> getCommunicationTemplates(CommunicationType communicationType) {

        log.info("getCommunicationTemplate service called");

        return communicationTemplateRepository.findAllByCommunicationType(communicationType);
    }

}
