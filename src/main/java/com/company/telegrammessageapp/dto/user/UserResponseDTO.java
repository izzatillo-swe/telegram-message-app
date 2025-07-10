package com.company.telegrammessageapp.dto.user;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String username,
        LocalDateTime createdDate
) {
}
