package com.company.telegrammessageapp.controller;


import com.company.telegrammessageapp.dto.message.MessageRequestDTO;
import com.company.telegrammessageapp.dto.message.MessageResponseDTO;
import com.company.telegrammessageapp.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
@Tag(name = "Message")
public class MessageController {

    private final MessageService messageService;

    @Operation(description = "POST endpoint to send a message")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<MessageResponseDTO> send(@RequestBody @Valid MessageRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.sendMessage(dto.message()));
    }

    @Operation(description = "GET endpoint to get user messages")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> getUserMessages() {
        return ResponseEntity.ok(messageService.getUserMessages());
    }

}