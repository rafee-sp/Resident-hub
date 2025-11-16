package com.rafee.residenthub.exception;

public class InvalidTwilioResponseException extends RuntimeException{

    public InvalidTwilioResponseException(String message) {
        super(message);
    }
}
