package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BroadcastRequest {

    @NotBlank(message = "Broadcast title is required")
    private String title;

    @NotBlank(message = "Broadcast type is required")
    private String broadcastType;

    @NotBlank(message = "Message content is required")
    private String messageContent;

    private Integer buildingId;

    private List<Integer> tagIds;

}
