package com.rafee.residenthub.mapper;

import com.rafee.residenthub.dto.response.BuildingsDTO;
import com.rafee.residenthub.entity.Building;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BuildingsMapper {

    BuildingsDTO toDTO(Building building);

    List<BuildingsDTO> toListDTO(List<Building> buildings);
}
