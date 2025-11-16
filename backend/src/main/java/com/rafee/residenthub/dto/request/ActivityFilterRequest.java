package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ActivityFilterRequest {

    private String actionType;

    private LocalDate activityDate;

    @Min(value = 0, message = "Page minimum value is 0")
    private int page;

    @Min(value = 10, message = "Size minimum value is 10")
    @Max(value = 50, message = "Size minimum value is 50")
    private int size;
}
