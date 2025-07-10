package com.company.telegrammessageapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class RestException extends RuntimeException {

    private final HttpStatus status;

    private RestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static RestException notFound(String message) {
        return new RestException(message, HttpStatus.NOT_FOUND);
    }

    public static RestException badRequest(String message) {
        return new RestException(message, HttpStatus.BAD_REQUEST);
    }

    public static RestException restThrow(String message, HttpStatus status) {
        return new RestException(message, status);
    }

}
