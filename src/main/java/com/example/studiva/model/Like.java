package com.example.studiva.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

// "LIKE" est un mot réservé en SQL : on renomme l'entité et la table
@Entity(name = "LikePublication")
@Table(name = "likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"etudiant_id", "publication_id"})
)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateLike;

    // Étudiant qui aime
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    // Publication aimée
    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @PrePersist
    public void avantEnregistrement() {
        this.dateLike = LocalDateTime.now();
    }

    // ===== Getters et Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateLike() { return dateLike; }
    public void setDateLike(LocalDateTime dateLike) { this.dateLike = dateLike; }

    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }

    public Publication getPublication() { return publication; }
    public void setPublication(Publication publication) { this.publication = publication; }
}