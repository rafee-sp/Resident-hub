package com.rafee.residenthub.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse {

    private String message;
    private Object data;
    private PaginationInfo page;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    public ApiResponse(String message, Object data){
        this.message = message;
        this.data = data;
        this.localDateTime = LocalDateTime.now();
    }

    public ApiResponse(String message, Object data, Page<?> page){
        this.message = message;
        this.data = data;
        this.page = page != null ? new PaginationInfo(page) : null;
        this.localDateTime = LocalDateTime.now();
    }
}
