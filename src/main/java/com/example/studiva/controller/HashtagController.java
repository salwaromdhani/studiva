package com.example.studiva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studiva.model.Hashtag;
import com.example.studiva.service.HashtagService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hashtags")
public class HashtagController {

    private final HashtagService service;

    public HashtagController(HashtagService service) {
        this.service = service;
    }

    @GetMapping
    public List<Hashtag> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Hashtag getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Hashtag> create(@Valid @RequestBody Hashtag hashtag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(hashtag));
    }

    @PutMapping("/{id}")
    public Hashtag update(@PathVariable Long id, @Valid @RequestBody Hashtag hashtag) {
        return service.update(id, hashtag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}