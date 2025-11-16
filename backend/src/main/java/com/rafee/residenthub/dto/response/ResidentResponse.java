package com.rafee.residenthub.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ResidentResponse {

    private Long Id;
    private String residentName;
    private String phoneNumber;
    private Integer buildingId;
    private String flatNo;
    private List<Integer> tagIds;
}
