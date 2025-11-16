package com.rafee.residenthub.util;

import com.rafee.residenthub.dto.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {

    private ResponseUtil() {

    }

    public static <T> ResponseEntity<ApiResponse> buildPagedResponse(Page<T> page, String successMsg, String emptyMsg) {

        List<T> list = page.getContent();

        if(list.isEmpty()){
            return ResponseEntity.ok(new ApiResponse(emptyMsg, list));
        }

        return ResponseEntity.ok(new ApiResponse(successMsg, list, page));

    }

}
