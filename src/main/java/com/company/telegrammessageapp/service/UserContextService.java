package com.company.telegrammessageapp.service;


import com.company.telegrammessageapp.entity.User;
import com.company.telegrammessageapp.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextService {

    public static User getCurrentUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
            throw RestException.restThrow("auth.unauthorized", HttpStatus.UNAUTHORIZED);

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User))
            throw RestException.restThrow("auth.invalid.principal", HttpStatus.UNAUTHORIZED);

        return (User) principal;
    }

}