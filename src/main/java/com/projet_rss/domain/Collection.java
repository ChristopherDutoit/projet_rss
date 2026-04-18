package com.projet_rss.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "collections")
public class Collection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;



    private String name;

    @ManyToMany(mappedBy = "collections") 
    @JsonIgnore 
    private Set<Article> articles = new HashSet<>();

    public Collection() {} 

    public Collection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public Set<Article> getArticles() {
        return articles;
    }
    
}
