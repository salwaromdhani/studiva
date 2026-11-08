package com.example.studiva.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Column(nullable = false, unique = true)
    private String email;

    // Accepté quand on l'envoie, mais jamais affiché dans les réponses
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, max = 100, message = "Le mot de passe doit contenir au moins 8 caractères")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String motDePasse;

    @Column(nullable = false, updatable = false)
    private LocalDate dateInscription;

    // Un étudiant peut écrire plusieurs publications
    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Publication> publications = new ArrayList<>();

    // Un étudiant peut écrire plusieurs commentaires
    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Commentaire> commentaires = new ArrayList<>();

    // Un étudiant peut aimer plusieurs publications
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    // Étudiants que je suis
    @OneToMany(mappedBy = "abonne", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Abonnement> abonnements = new ArrayList<>();

    // Étudiants qui me suivent
    @OneToMany(mappedBy = "suivi", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Abonnement> abonnes = new ArrayList<>();

    // Remplit la date automatiquement avant l'enregistrement
    @PrePersist
    public void avantEnregistrement() {
        this.dateInscription = LocalDate.now();
    }

    // ===== Getters et Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) { this.dateInscription = dateInscription; }

    public List<Publication> getPublications() { return publications; }
    public void setPublications(List<Publication> publications) { this.publications = publications; }

    public List<Commentaire> getCommentaires() { return commentaires; }
    public void setCommentaires(List<Commentaire> commentaires) { this.commentaires = commentaires; }

    public List<Like> getLikes() { return likes; }
    public void setLikes(List<Like> likes) { this.likes = likes; }

    public List<Abonnement> getAbonnements() { return abonnements; }
    public void setAbonnements(List<Abonnement> abonnements) { this.abonnements = abonnements; }

    public List<Abonnement> getAbonnes() { return abonnes; }
    public void setAbonnes(List<Abonnement> abonnes) { this.abonnes = abonnes; }
}