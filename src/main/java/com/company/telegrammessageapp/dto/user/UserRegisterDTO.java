package com.company.telegrammessageapp.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserRegisterDTO(

        @NotBlank
        @Schema(example = "Joseph", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @NotBlank
        @Schema(example = "joseph_01", requiredMode = Schema.RequiredMode.REQUIRED)
        String username,

        @NotBlank
        @Schema(example = "password", requiredMode = Schema.RequiredMode.REQUIRED)
        String password

) implements Serializable {
}
