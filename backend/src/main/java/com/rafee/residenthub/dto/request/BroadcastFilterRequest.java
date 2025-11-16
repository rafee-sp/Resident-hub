package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BroadcastFilterRequest {

    @NotBlank(message = "Broadcast Mode is required")
    private String broadcastMode;

    @NotBlank(message = "Broadcast status is required")
    private String status;

    private LocalDate broadcastDate;

    @Min(value = 0, message = "Page minimum value is 0")
    private int page;

    @Min(value = 10, message = "Size minimum value is 10")
    @Max(value = 50, message = "Size minimum value is 50")
    private int size;
}
