package com.example.studiva.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.studiva.model.Abonnement;
import com.example.studiva.model.Etudiant;
import com.example.studiva.repository.AbonnementRepository;
import com.example.studiva.repository.EtudiantRepository;

@Service
public class AbonnementService {

    private final AbonnementRepository repository;
    private final EtudiantRepository etudiantRepository;

    public AbonnementService(AbonnementRepository repository,
                             EtudiantRepository etudiantRepository) {
        this.repository = repository;
        this.etudiantRepository = etudiantRepository;
    }

    public List<Abonnement> findAll() {
        return repository.findAll();
    }

    public Abonnement findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Abonnement introuvable"));
    }

    public Abonnement save(Long abonneId, Long suiviId) {
        // Règle métier 1 : on ne peut pas se suivre soi-même
        if (abonneId.equals(suiviId)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Un étudiant ne peut pas se suivre lui-même");
        }
        // Règle métier 2 : pas d'abonnement en double
        if (repository.existsByAbonneIdAndSuiviId(abonneId, suiviId)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Abonnement déjà existant");
        }

        Etudiant abonne = etudiantRepository.findById(abonneId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Abonné introuvable"));

        Etudiant suivi = etudiantRepository.findById(suiviId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Étudiant suivi introuvable"));

        Abonnement abonnement = new Abonnement();
        abonnement.setAbonne(abonne);
        abonnement.setSuivi(suivi);
        return repository.save(abonnement);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public List<Abonnement> abonnementsEtudiant(Long id) {
        return repository.findByAbonneId(id);
    }

    public List<Abonnement> abonnesEtudiant(Long id) {
        return repository.findBySuiviId(id);
    }
}