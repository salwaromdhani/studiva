package com.example.studiva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studiva.model.Like;
import com.example.studiva.service.LikeService;

// Un like ne se modifie pas : on aime ou on retire (pas de PUT)
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService service;

    public LikeController(LikeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Like> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Like getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/likes?etudiantId=1&publicationId=1 → 201
    @PostMapping
    public ResponseEntity<Like> create(@RequestParam Long etudiantId,
                                       @RequestParam Long publicationId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(etudiantId, publicationId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/likes/publication/1/count
    @GetMapping("/publication/{id}/count")
    public long nombreLikes(@PathVariable Long id) {
        return service.nombreLikes(id);
    }
}