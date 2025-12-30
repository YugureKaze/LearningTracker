package com.yugurekaze.learningtracker.user.model.dto;

import java.time.LocalDateTime;

public record UserResponse(

        String email,
        LocalDateTime createdAt
) {
}
