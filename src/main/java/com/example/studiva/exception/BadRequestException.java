package com.example.studiva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Erreur 400 : la demande ne respecte pas une règle métier
public class BadRequestException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}