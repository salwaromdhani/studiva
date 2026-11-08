package com.example.studiva.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.studiva.model.Commentaire;
import com.example.studiva.model.Etudiant;
import com.example.studiva.model.Publication;
import com.example.studiva.repository.CommentaireRepository;
import com.example.studiva.repository.EtudiantRepository;
import com.example.studiva.repository.PublicationRepository;

@Service
public class CommentaireService {

    private final CommentaireRepository repository;
    private final EtudiantRepository etudiantRepository;
    private final PublicationRepository publicationRepository;

    public CommentaireService(CommentaireRepository repository,
                              EtudiantRepository etudiantRepository,
                              PublicationRepository publicationRepository) {
        this.repository = repository;
        this.etudiantRepository = etudiantRepository;
        this.publicationRepository = publicationRepository;
    }

    public List<Commentaire> findAll() {
        return repository.findAll();
    }

    public Commentaire findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Commentaire introuvable"));
    }

    public Commentaire save(Commentaire commentaire, Long auteurId, Long publicationId) {
        Etudiant auteur = etudiantRepository.findById(auteurId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Étudiant introuvable"));

        Publication publication = publicationRepository.findById(publicationId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Publication introuvable"));

        commentaire.setAuteur(auteur);
        commentaire.setPublication(publication);
        return repository.save(commentaire);
    }

    public Commentaire update(Long id, Commentaire nouveau) {
        Commentaire ancien = findById(id);
        ancien.setContenu(nouveau.getContenu());
        return repository.save(ancien);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    // Requête personnalisée 3
    public List<Commentaire> commentairesPublication(Long id) {
        return repository.findByPublicationIdOrderByDateCommentaireAsc(id);
    }
}