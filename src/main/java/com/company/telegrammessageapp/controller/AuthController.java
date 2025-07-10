package com.company.telegrammessageapp.controller;


import com.company.telegrammessageapp.dto.token.TokenDTO;
import com.company.telegrammessageapp.dto.user.RefreshTokenDTO;
import com.company.telegrammessageapp.dto.user.UserLoginDTO;
import com.company.telegrammessageapp.dto.user.UserRegisterDTO;
import com.company.telegrammessageapp.dto.user.UserResponseDTO;
import com.company.telegrammessageapp.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    @Operation(description = "POST endpoint to register a new user")
    @PostMapping
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }

    @Operation(description = "POST endpoint to login a user")
    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserLoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @Operation(description = "POST endpoint to refresh access token using a refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody @Valid RefreshTokenDTO dto) {
        return ResponseEntity.ok(authService.refreshToken(dto.refreshToken()));
    }

}