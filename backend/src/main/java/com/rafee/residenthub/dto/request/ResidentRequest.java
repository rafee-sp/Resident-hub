package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResidentRequest {

    @NotBlank(message = "Resident name is required")
    private String residentName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Building Id is required")
    private Integer buildingId;

    @NotBlank(message = "Flat number is required")
    private String flatNo;

    private List<Integer> tagIds = new ArrayList<>();

}
