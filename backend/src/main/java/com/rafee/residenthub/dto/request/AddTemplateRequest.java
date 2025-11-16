package com.rafee.residenthub.dto.request;

import com.rafee.residenthub.dto.enums.CommunicationType;
import lombok.Data;

@Data
public class AddTemplateRequest {

    private String template;
    private CommunicationType communicationType;
}
