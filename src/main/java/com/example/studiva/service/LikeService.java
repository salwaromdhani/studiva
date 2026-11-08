package com.example.studiva.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.studiva.model.Etudiant;
import com.example.studiva.model.Like;
import com.example.studiva.model.Publication;
import com.example.studiva.repository.EtudiantRepository;
import com.example.studiva.repository.LikeRepository;
import com.example.studiva.repository.PublicationRepository;

@Service
public class LikeService {

    private final LikeRepository repository;
    private final EtudiantRepository etudiantRepository;
    private final PublicationRepository publicationRepository;

    public LikeService(LikeRepository repository,
                       EtudiantRepository etudiantRepository,
                       PublicationRepository publicationRepository) {
        this.repository = repository;
        this.etudiantRepository = etudiantRepository;
        this.publicationRepository = publicationRepository;
    }

    public List<Like> findAll() {
        return repository.findAll();
    }

    public Like findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Like introuvable"));
    }

    public Like save(Long etudiantId, Long publicationId) {
        // Règle métier : un seul like par étudiant et par publication
        if (repository.existsByEtudiantIdAndPublicationId(etudiantId, publicationId)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Publication déjà aimée");
        }

        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Étudiant introuvable"));

        Publication publication = publicationRepository.findById(publicationId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Publication introuvable"));

        Like like = new Like();
        like.setEtudiant(etudiant);
        like.setPublication(publication);
        return repository.save(like);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public long nombreLikes(Long publicationId) {
        return repository.countByPublicationId(publicationId);
    }
}