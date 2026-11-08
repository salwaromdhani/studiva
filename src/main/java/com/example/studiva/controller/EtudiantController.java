package com.example.studiva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studiva.model.Etudiant;
import com.example.studiva.service.EtudiantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService service;

    public EtudiantController(EtudiantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Etudiant> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Etudiant getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/etudiants → 201
    @PostMapping
    public ResponseEntity<Etudiant> create(@Valid @RequestBody Etudiant etudiant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(etudiant));
    }

    @PutMapping("/{id}")
    public Etudiant update(@PathVariable Long id, @Valid @RequestBody Etudiant etudiant) {
        return service.update(id, etudiant);
    }

    // DELETE /api/etudiants/1 → 204
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}