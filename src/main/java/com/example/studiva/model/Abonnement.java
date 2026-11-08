package com.example.studiva.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "abonnements",
    uniqueConstraints = @UniqueConstraint(columnNames = {"abonne_id", "suivi_id"})
)
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateAbonnement;

    // Étudiant qui suit
    @ManyToOne
    @JoinColumn(name = "abonne_id", nullable = false)
    private Etudiant abonne;

    // Étudiant suivi
    @ManyToOne
    @JoinColumn(name = "suivi_id", nullable = false)
    private Etudiant suivi;

    @PrePersist
    public void avantEnregistrement() {
        this.dateAbonnement = LocalDateTime.now();
    }

    // ===== Getters et Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateAbonnement() { return dateAbonnement; }
    public void setDateAbonnement(LocalDateTime dateAbonnement) { this.dateAbonnement = dateAbonnement; }

    public Etudiant getAbonne() { return abonne; }
    public void setAbonne(Etudiant abonne) { this.abonne = abonne; }

    public Etudiant getSuivi() { return suivi; }
    public void setSuivi(Etudiant suivi) { this.suivi = suivi; }
}