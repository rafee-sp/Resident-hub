package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.response.BuildingsDTO;
import com.rafee.residenthub.entity.Building;

import java.util.List;

public interface BuildingService {

    Building getBuilding(int id);

    String getBuildingName(int id);

    List<BuildingsDTO> getAllBuildings();
}
