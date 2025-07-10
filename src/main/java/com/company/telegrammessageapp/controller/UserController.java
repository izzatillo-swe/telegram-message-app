package com.company.telegrammessageapp.controller;


import com.company.telegrammessageapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @Operation(description = "POST endpoint to generate telegram token")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("generate-telegram-token")
    public ResponseEntity<String> generateToken() {
        return ResponseEntity.ok(userService.generateTelegramToken());
    }

}