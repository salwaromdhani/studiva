package com.example.studiva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Erreur 404 : l'élément demandé n'existe pas (étudiant, publication...)
public class NotFoundException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}