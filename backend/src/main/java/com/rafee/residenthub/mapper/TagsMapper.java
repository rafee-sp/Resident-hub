package com.rafee.residenthub.mapper;

import com.rafee.residenthub.dto.response.TagsDTO;
import com.rafee.residenthub.entity.Tags;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagsMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    TagsDTO toDTO(Tags tags);

    List<TagsDTO> toListDTO(List<Tags> tagsList);

}
