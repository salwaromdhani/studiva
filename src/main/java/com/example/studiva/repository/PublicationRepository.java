package com.example.studiva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.studiva.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    // Requête personnalisée 1 : publications d'un étudiant
    @Query("SELECT p FROM Publication p WHERE p.auteur.id = :id ORDER BY p.datePublication DESC")
    List<Publication> publicationsEtudiant(@Param("id") Long id);

    // Requête personnalisée 2 : fil d'actualité
    @Query("SELECT p FROM Publication p ORDER BY p.datePublication DESC")
    List<Publication> filActualite();
}