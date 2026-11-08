package com.example.studiva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studiva.model.Publication;
import com.example.studiva.service.PublicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    private final PublicationService service;

    public PublicationController(PublicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Publication> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Publication getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/publications?auteurId=1 → 201
    @PostMapping
    public ResponseEntity<Publication> create(@Valid @RequestBody Publication publication,
                                              @RequestParam Long auteurId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(publication, auteurId));
    }

    @PutMapping("/{id}")
    public Publication update(@PathVariable Long id, @Valid @RequestBody Publication publication) {
        return service.update(id, publication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Requête personnalisée 1 : GET /api/publications/etudiant/1
    @GetMapping("/etudiant/{id}")
    public List<Publication> publicationsEtudiant(@PathVariable Long id) {
        return service.publicationsEtudiant(id);
    }

    // Requête personnalisée 2 : GET /api/publications/fil-actualite
    @GetMapping("/fil-actualite")
    public List<Publication> filActualite() {
        return service.filActualite();
    }

    // POST /api/publications/1/hashtags/2 → associer le hashtag 2 à la publication 1
    @PostMapping("/{id}/hashtags/{hashtagId}")
    public Publication ajouterHashtag(@PathVariable Long id, @PathVariable Long hashtagId) {
        return service.ajouterHashtag(id, hashtagId);
    }
}