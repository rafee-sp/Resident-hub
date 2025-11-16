package com.rafee.residenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest (

        @NotBlank(message = "New password is required")
        @Size(min = 8, max = 20, message = "Password must be 8–20 characters long")
        String newPassword,

        @NotBlank(message = "Current password is required")
        @Size(min = 8, max = 20, message = "Password must be 8–20 characters long")
        String currentPassword
) {
}
