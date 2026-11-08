package com.example.studiva.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.studiva.model.Etudiant;
import com.example.studiva.model.Hashtag;
import com.example.studiva.model.Publication;
import com.example.studiva.repository.EtudiantRepository;
import com.example.studiva.repository.HashtagRepository;
import com.example.studiva.repository.PublicationRepository;

@Service
public class PublicationService {

    private final PublicationRepository repository;
    private final EtudiantRepository etudiantRepository;
    private final HashtagRepository hashtagRepository;

    public PublicationService(PublicationRepository repository,
                              EtudiantRepository etudiantRepository,
                              HashtagRepository hashtagRepository) {
        this.repository = repository;
        this.etudiantRepository = etudiantRepository;
        this.hashtagRepository = hashtagRepository;
    }

    public List<Publication> findAll() {
        return repository.findAll();
    }

    public Publication findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Publication introuvable"));
    }

    // Créer une publication avec l'ID de son auteur
    public Publication save(Publication publication, Long auteurId) {
        Etudiant auteur = etudiantRepository.findById(auteurId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Étudiant introuvable"));
        publication.setAuteur(auteur);
        return repository.save(publication);
    }

    public Publication update(Long id, Publication nouvelle) {
        Publication ancienne = findById(id);
        ancienne.setContenu(nouvelle.getContenu());
        ancienne.setImageUrl(nouvelle.getImageUrl());
        return repository.save(ancienne);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    // Requête personnalisée 1
    public List<Publication> publicationsEtudiant(Long id) {
        etudiantRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Étudiant introuvable"));
        return repository.publicationsEtudiant(id);
    }

    // Requête personnalisée 2
    public List<Publication> filActualite() {
        return repository.filActualite();
    }

    // Associer un hashtag à une publication
    @Transactional
    public Publication ajouterHashtag(Long publicationId, Long hashtagId) {
        Publication publication = findById(publicationId);
        Hashtag hashtag = hashtagRepository.findById(hashtagId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Hashtag introuvable"));
        if (publication.getHashtags().contains(hashtag)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Ce hashtag est déjà associé à la publication");
        }
        publication.getHashtags().add(hashtag);
        return repository.save(publication);
    }
}