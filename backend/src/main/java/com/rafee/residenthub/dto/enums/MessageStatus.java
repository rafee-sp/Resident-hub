package com.rafee.residenthub.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

public enum MessageStatus {

    QUEUED("Queued"),
    INITIATED("Initiated"),
    DELIVERED("Delivered"),
    FAILED("Failed"),
    UNDELIVERED("Undelivered"),
    COMPLETED("completed"),
    NO_ANSWER("No Answer");

    private final String label;

    MessageStatus(String label) { this.label = label;}

    @JsonValue
    private String getLabel() { return label;}

    public static boolean isValidTextMessageStatus(String status) {

        if(!StringUtils.hasText(status)) return false;

        try {
            MessageStatus.valueOf(status.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static MessageStatus getVoiceMessageStatus(String status) {

        if(!StringUtils.hasText(status)) return FAILED;

        try {
            return switch (status.toLowerCase()) {
                case "completed" -> COMPLETED;
                case "no-answer" -> NO_ANSWER;
                default -> FAILED; // failed, cancelled, busy
            };
        } catch (IllegalArgumentException e) {
            return FAILED;
        }
    }
}
