package com.rafee.residenthub.mapper;

import com.rafee.residenthub.dto.request.ResidentRequest;
import com.rafee.residenthub.dto.response.ResidentResponse;
import com.rafee.residenthub.entity.Resident;
import com.rafee.residenthub.entity.Tags;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ResidentMapper {

    Resident toEntity(ResidentRequest residentRequest);

    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "tags", target = "tagIds", qualifiedByName = "mapTagsToStringList")
    ResidentResponse toDTO(Resident resident);

    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "tags", target = "tagIds", qualifiedByName = "mapTagsToStringList")
    List<ResidentResponse> toListDTO(List<Resident> residentList);

    @Named("mapTagsToStringList")
    default List<Integer> mapTagsToTagDTOList(Set<Tags> tags) {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }
        return tags.stream()
                .map(Tags::getId)
                .collect(Collectors.toList());
    }

}
