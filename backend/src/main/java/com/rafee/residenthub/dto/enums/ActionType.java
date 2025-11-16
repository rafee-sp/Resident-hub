package com.rafee.residenthub.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {

    RESIDENT_CREATE("Resident Created"),
    RESIDENT_UPDATE("Resident Updated"),
    RESIDENT_DELETE("Resident Deleted"),
    VOICE_MESSAGE_SEND("Voice Message Sent"),
    TEXT_MESSAGE_SEND("Text Message Sent"),
    BROADCAST("Broadcast"),
    USER_CREATION("User Created"),
    USER_DELETION("User Deleted"),
    PASSWORD_RESET("Password Reset"),
    TEXT_MESSAGE_CALLBACK("Text Message Callback"),
    VOICE_MESSAGE_CALLBACK("Voice Message Callback");

    private final String label;

    ActionType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    public static ActionType fromString(String name) {
        try {
            return ActionType.valueOf(name);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ActionType: " + name);
        }
    }

}
