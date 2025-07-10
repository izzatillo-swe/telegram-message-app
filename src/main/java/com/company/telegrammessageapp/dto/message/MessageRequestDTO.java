package com.company.telegrammessageapp.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MessageRequestDTO(
        @NotBlank
        @Schema(example = "I am writing a message to receive it from bot", requiredMode = Schema.RequiredMode.REQUIRED)
        String message
) {
}
