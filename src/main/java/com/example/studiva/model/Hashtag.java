package com.example.studiva.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du hashtag est obligatoire")
    @Size(max = 50)
    @Pattern(regexp = "^#?[\\p{L}0-9_]+$", message = "Hashtag invalide (pas d'espace)")
    @Column(nullable = false, unique = true, length = 50)
    private String nom;

    // Relation inverse avec Publication
    @ManyToMany(mappedBy = "hashtags")
    @JsonIgnore
    private Set<Publication> publications = new HashSet<>();

    // ===== Getters et Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Set<Publication> getPublications() { return publications; }
    public void setPublications(Set<Publication> publications) { this.publications = publications; }
}