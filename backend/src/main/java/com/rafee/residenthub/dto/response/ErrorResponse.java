package com.rafee.residenthub.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ErrorResponse{

    private int statusCode;
    private Object message;
    private String errorCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    public ErrorResponse(int statusCode, Object message){
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    public ErrorResponse(int statusCode, Object message, String errorCode){
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}
