package com.example.studiva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studiva.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // Vérifier si un étudiant a déjà aimé une publication
    boolean existsByEtudiantIdAndPublicationId(Long etudiantId, Long publicationId);

    // Compter les likes d'une publication
    long countByPublicationId(Long publicationId);
}