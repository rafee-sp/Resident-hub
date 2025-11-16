package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageRequest {

    @NotNull(message = "Resident Id is required")
    private Long residentId;

    @NotBlank(message = "Message content is required")
    @Size(min = 1, max = 250, message = "Message content must be between 1 - 250 characters long")
    private String messageContent;


}
