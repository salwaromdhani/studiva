package com.example.studiva.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studiva.model.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // Recherche d'un étudiant par son email
    Optional<Etudiant> findByEmail(String email);
}