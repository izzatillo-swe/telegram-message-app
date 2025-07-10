package com.company.telegrammessageapp.dto.message;

import java.time.LocalDateTime;

public record MessageResponseDTO(
        Long id,
        String body,
        LocalDateTime sentAt
) {
}
