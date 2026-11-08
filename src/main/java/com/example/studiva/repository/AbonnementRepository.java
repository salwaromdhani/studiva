package com.example.studiva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studiva.model.Abonnement;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

    // Les étudiants que je suis
    List<Abonnement> findByAbonneId(Long id);

    // Mes abonnés
    List<Abonnement> findBySuiviId(Long id);

    boolean existsByAbonneIdAndSuiviId(Long abonneId, Long suiviId);
}