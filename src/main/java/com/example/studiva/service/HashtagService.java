package com.example.studiva.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.studiva.model.Hashtag;
import com.example.studiva.model.Publication;
import com.example.studiva.repository.HashtagRepository;

@Service
public class HashtagService {

    private final HashtagRepository repository;

    public HashtagService(HashtagRepository repository) {
        this.repository = repository;
    }

    public List<Hashtag> findAll() {
        return repository.findAll();
    }

    public Hashtag findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Hashtag introuvable"));
    }

    public Hashtag save(Hashtag hashtag) {
        // Règle métier : pas deux hashtags avec le même nom
        if (repository.findByNom(hashtag.getNom()).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Ce hashtag existe déjà");
        }
        return repository.save(hashtag);
    }

    public Hashtag update(Long id, Hashtag nouveau) {
        Hashtag ancien = findById(id);
        ancien.setNom(nouveau.getNom());
        return repository.save(ancien);
    }

    @Transactional
    public void delete(Long id) {
        Hashtag hashtag = findById(id);
        // On retire d'abord le hashtag des publications, puis on le supprime
        for (Publication p : hashtag.getPublications()) {
            p.getHashtags().remove(hashtag);
        }
        repository.delete(hashtag);
    }
}