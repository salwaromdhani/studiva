package com.example.studiva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studiva.model.Commentaire;
import com.example.studiva.service.CommentaireService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    private final CommentaireService service;

    public CommentaireController(CommentaireService service) {
        this.service = service;
    }

    @GetMapping
    public List<Commentaire> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Commentaire getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/commentaires?auteurId=1&publicationId=1 → 201
    @PostMapping
    public ResponseEntity<Commentaire> create(@Valid @RequestBody Commentaire commentaire,
                                              @RequestParam Long auteurId,
                                              @RequestParam Long publicationId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(commentaire, auteurId, publicationId));
    }

    @PutMapping("/{id}")
    public Commentaire update(@PathVariable Long id, @Valid @RequestBody Commentaire commentaire) {
        return service.update(id, commentaire);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Requête personnalisée 3 : GET /api/commentaires/publication/1
    @GetMapping("/publication/{id}")
    public List<Commentaire> commentairesPublication(@PathVariable Long id) {
        return service.commentairesPublication(id);
    }
}