package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddBuildingRequest {

    @NotBlank(message = "Building name is required")
    private String buildingName;
}
