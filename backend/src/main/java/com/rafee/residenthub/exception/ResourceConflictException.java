package com.rafee.residenthub.exception;

import lombok.Getter;

@Getter
public class ResourceConflictException extends RuntimeException{

    private final String field;

    public ResourceConflictException(String message, String field){
        super(message);
        this.field = field;
    }

}
