package com.rafee.residenthub.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String userName;
    private String password;
}
