package org.example.projetcloudback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "article")  // Utilisez le nom exact de la table dans votre base de données
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Utilisez Identity ou UUID selon votre besoin
    private Long id;

    private String name;

    private String description;

    private String image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Constructeurs, getters et setters
    public Article() {}

    public Article(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}