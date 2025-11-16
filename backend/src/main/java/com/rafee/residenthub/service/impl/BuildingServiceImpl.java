package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.response.BuildingsDTO;
import com.rafee.residenthub.entity.Building;
import com.rafee.residenthub.exception.ResourceNotFoundException;
import com.rafee.residenthub.mapper.BuildingsMapper;
import com.rafee.residenthub.repository.BuildingsRepository;
import com.rafee.residenthub.service.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildingServiceImpl implements BuildingService {

    private final BuildingsRepository buildingsRepository;
    private final BuildingsMapper buildingsMapper;

    @Override
    public Building getBuilding(int id) {
        return buildingsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Building not found "+id));
    }

    @Override
    public String getBuildingName(int id) {

        log.info("getBuildingName called for {}", id);

        return buildingsRepository.findBuildingNameById(id).orElseThrow(() -> new ResourceNotFoundException("Building not found " + id));
    }

    @Override
    public List<BuildingsDTO> getAllBuildings() {
        return buildingsMapper.toListDTO(buildingsRepository.findAll());
    }
}
