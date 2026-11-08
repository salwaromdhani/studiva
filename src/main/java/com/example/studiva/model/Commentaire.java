package com.example.studiva.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "commentaires")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le commentaire est obligatoire")
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String contenu;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCommentaire;

    // Auteur du commentaire
    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private Etudiant auteur;

    // Publication commentée
    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @PrePersist
    public void avantEnregistrement() {
        this.dateCommentaire = LocalDateTime.now();
    }

    // ===== Getters et Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getDateCommentaire() { return dateCommentaire; }
    public void setDateCommentaire(LocalDateTime dateCommentaire) { this.dateCommentaire = dateCommentaire; }

    public Etudiant getAuteur() { return auteur; }
    public void setAuteur(Etudiant auteur) { this.auteur = auteur; }

    public Publication getPublication() { return publication; }
    public void setPublication(Publication publication) { this.publication = publication; }
}