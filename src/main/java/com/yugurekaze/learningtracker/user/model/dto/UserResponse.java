package com.yugurekaze.learningtracker.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UserResponse(
        @NotBlank
        @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
        @Size(max = 255)
        String email,

        @NotNull
        LocalDateTime createdAt
) {
}
