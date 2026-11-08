package com.example.studiva.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.studiva.model.Etudiant;
import com.example.studiva.repository.EtudiantRepository;

@Service
public class EtudiantService {

    private final EtudiantRepository repository;

    public EtudiantService(EtudiantRepository repository) {
        this.repository = repository;
    }

    // Afficher tous les étudiants
    public List<Etudiant> findAll() {
        return repository.findAll();
    }

    // Rechercher un étudiant par ID
    public Etudiant findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Étudiant introuvable"));
    }

    // Ajouter un étudiant
    public Etudiant save(Etudiant etudiant) {
        // Règle métier : un email ne peut être utilisé qu'une seule fois
        if (repository.findByEmail(etudiant.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Cet email est déjà utilisé");
        }
        return repository.save(etudiant);
    }

    // Modifier un étudiant
    public Etudiant update(Long id, Etudiant etudiant) {
        Etudiant ancien = findById(id);
        ancien.setNom(etudiant.getNom());
        ancien.setPrenom(etudiant.getPrenom());
        ancien.setEmail(etudiant.getEmail());
        ancien.setMotDePasse(etudiant.getMotDePasse());
        return repository.save(ancien);
    }

    // Supprimer un étudiant
    public void delete(Long id) {
        Etudiant etudiant = findById(id);
        repository.delete(etudiant);
    }
}