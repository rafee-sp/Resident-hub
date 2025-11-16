package com.rafee.residenthub.dto.response;

import java.util.List;

public record MetaDataResponse(List<BuildingsDTO> buildingsList, List<TagsDTO> tagsList) {
}
