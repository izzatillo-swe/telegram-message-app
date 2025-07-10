package com.company.telegrammessageapp.exception.handler;


import com.company.telegrammessageapp.exception.AppErrorDTO;
import com.company.telegrammessageapp.exception.RestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public HttpEntity<List<AppErrorDTO>> handle(MethodArgumentNotValidException ex) {
        List<AppErrorDTO> errors = new LinkedList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(
                    new AppErrorDTO(
                            request.getRequestURI(),
                            400,
                            fieldError.getDefaultMessage(),
                            fieldError.getField()
            ));
        }
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(value = RestException.class)
    public HttpEntity<AppErrorDTO> handle(RestException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new AppErrorDTO(request.getRequestURI(), ex.getStatus().value(), ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HttpEntity<AppErrorDTO> handle(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        return ResponseEntity
                .status(400)
                .body(new AppErrorDTO(request.getRequestURI(), 400, ex.getMessage()));
    }

}