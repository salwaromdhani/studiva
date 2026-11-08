package com.example.studiva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studiva.model.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    // Requête personnalisée 3 : commentaires d'une publication
    List<Commentaire> findByPublicationIdOrderByDateCommentaireAsc(Long publicationId);
}