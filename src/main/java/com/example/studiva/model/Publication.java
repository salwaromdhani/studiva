package com.example.studiva.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le contenu est obligatoire")
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String contenu;

    @Size(max = 255)
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime datePublication;

    // Plusieurs publications appartiennent à un étudiant
    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private Etudiant auteur;

    // Une publication a plusieurs commentaires
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Commentaire> commentaires = new ArrayList<>();

    // Une publication a plusieurs likes
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    // Plusieurs publications peuvent avoir plusieurs hashtags
    @ManyToMany
    @JoinTable(
        name = "publication_hashtags",
        joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> hashtags = new HashSet<>();

    @PrePersist
    public void avantEnregistrement() {
        this.datePublication = LocalDateTime.now();
    }

    // ===== Getters et Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDateTime datePublication) { this.datePublication = datePublication; }

    public Etudiant getAuteur() { return auteur; }
    public void setAuteur(Etudiant auteur) { this.auteur = auteur; }

    public List<Commentaire> getCommentaires() { return commentaires; }
    public void setCommentaires(List<Commentaire> commentaires) { this.commentaires = commentaires; }

    public List<Like> getLikes() { return likes; }
    public void setLikes(List<Like> likes) { this.likes = likes; }

    public Set<Hashtag> getHashtags() { return hashtags; }
    public void setHashtags(Set<Hashtag> hashtags) { this.hashtags = hashtags; }
}