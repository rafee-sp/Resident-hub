package com.rafee.residenthub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BroadcastSearchRequest {

    private Integer buildingId;

    private List<Integer> tagIds;

}
