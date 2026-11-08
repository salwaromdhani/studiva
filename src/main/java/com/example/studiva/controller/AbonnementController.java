package com.example.studiva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studiva.model.Abonnement;
import com.example.studiva.service.AbonnementService;

// Un abonnement ne se modifie pas : on suit ou on ne suit plus (pas de PUT)
@RestController
@RequestMapping("/api/abonnements")
public class AbonnementController {

    private final AbonnementService service;

    public AbonnementController(AbonnementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Abonnement> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Abonnement getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/abonnements?abonneId=1&suiviId=2 → 201
    @PostMapping
    public ResponseEntity<Abonnement> create(@RequestParam Long abonneId,
                                             @RequestParam Long suiviId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(abonneId, suiviId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Les étudiants que je suis
    @GetMapping("/etudiant/{id}/suivis")
    public List<Abonnement> abonnementsEtudiant(@PathVariable Long id) {
        return service.abonnementsEtudiant(id);
    }

    // Mes abonnés
    @GetMapping("/etudiant/{id}/abonnes")
    public List<Abonnement> abonnesEtudiant(@PathVariable Long id) {
        return service.abonnesEtudiant(id);
    }
}