package com.example.studiva.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

// Attrape les erreurs de tous les controllers et renvoie un message JSON clair
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Nos exceptions personnalisées : 404, 409, 400
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> erreurMetier(ResponseStatusException ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(erreur);
    }

    // Erreurs de validation (@NotBlank, @Email, @Size) → 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validation(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erreur ->
            erreurs.put(erreur.getField(), erreur.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(erreurs);
    }

    // JSON mal écrit dans le Body → 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> jsonInvalide(HttpMessageNotReadableException ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", "Le JSON envoyé est invalide");
        return ResponseEntity.badRequest().body(erreur);
    }

    // Mauvais type dans l'URL (ex : /api/etudiants/abc) → 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> mauvaisType(MethodArgumentTypeMismatchException ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", "Valeur invalide pour le paramètre : " + ex.getName());
        return ResponseEntity.badRequest().body(erreur);
    }

    // URL qui n'existe pas → 404
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> urlIntrouvable(NoResourceFoundException ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", "Cette URL n'existe pas");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erreur);
    }

    // Toute autre erreur imprévue → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> erreurGenerale(Exception ex) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("message", "Une erreur est survenue");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erreur);
    }
}