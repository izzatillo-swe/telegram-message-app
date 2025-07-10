package com.company.telegrammessageapp.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTO(
        @NotBlank
        @Schema(example = "refresh token", requiredMode = Schema.RequiredMode.REQUIRED)
        String refreshToken
) {}