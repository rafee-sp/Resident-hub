package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddUserRequest(
        @NotBlank(message = "User name is required")
        @Size(max = 45, message = "User name must not exceed 45 characters")
        String userName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 65, message = "Email must not exceed 65 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 20, message = "Password must be 8–20 characters long")
        String password
) {}
