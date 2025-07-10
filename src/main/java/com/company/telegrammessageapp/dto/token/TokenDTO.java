package com.company.telegrammessageapp.dto.token;

public record TokenDTO(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}
