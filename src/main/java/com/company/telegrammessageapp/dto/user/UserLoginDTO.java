package com.company.telegrammessageapp.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank
        @Schema(example = "joseph_01", requiredMode = Schema.RequiredMode.REQUIRED)
        String username,

        @NotBlank
        @Schema(example = "password", requiredMode = Schema.RequiredMode.REQUIRED)
        String password
) {
}
