package com.yugurekaze.learningtracker.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeEmailRequest(
        @NotBlank
        @Size(max = 255)
        @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
        String newEmail
) {
}
