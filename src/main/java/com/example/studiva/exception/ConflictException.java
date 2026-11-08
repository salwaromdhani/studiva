package com.example.studiva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Erreur 409 : la donnée existe déjà (email, like, abonnement en double...)
public class ConflictException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}