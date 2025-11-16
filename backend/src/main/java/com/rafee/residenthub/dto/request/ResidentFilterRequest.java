package com.rafee.residenthub.dto.request;

import com.rafee.residenthub.dto.enums.ResidentFilterType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResidentFilterRequest {

    @NotNull(message = "Filter type is required")
    private ResidentFilterType filterType;

    @NotBlank(message = "Filter value is required")
    private String filterValue;

    @Min(value = 0, message = "Page minimum value is 0")
    private int page;

    @Min(value = 10, message = "Size minimum value is 10")
    @Max(value = 50, message = "Size minimum value is 50")
    private int size;

}
