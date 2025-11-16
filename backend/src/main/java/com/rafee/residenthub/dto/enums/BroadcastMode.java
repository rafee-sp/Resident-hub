package com.rafee.residenthub.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BroadcastMode {

    TEXT_MESSAGE("Text"),
    VOICE("Voice");

    private final String label;

    BroadcastMode (String label){
        this.label = label;
    }

    @JsonValue
    private String getLabel() {
        return  label;
    }

}
