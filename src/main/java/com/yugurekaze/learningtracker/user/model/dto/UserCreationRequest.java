package com.yugurekaze.learningtracker.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreationRequest(
        @NotBlank
        @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
        @Size(max = 255)
        String email,
        @NotBlank
        @Size(min = 8, max = 255)
        String password
) {
}
