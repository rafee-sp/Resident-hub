package com.rafee.residenthub.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BroadcastStatus {

    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String label;

    BroadcastStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}

