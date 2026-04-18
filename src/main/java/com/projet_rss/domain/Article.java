package com.projet_rss.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="articles")
@EntityListeners(AuditingEntityListener.class)
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;
    
    @Column(unique = true, nullable = false)
    private String link;
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String description;

    private LocalDateTime pubDate;

   @ManyToOne(fetch = FetchType.LAZY) 
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "articles"})
    private Feed feed;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now(); 

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @ManyToMany
    @JoinTable(
    name = "article_collection",
    joinColumns = @JoinColumn(name = "article_id"),
    inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    
    private Set<Collection> collections = new HashSet<>();

 

    public Article() {}

    public Article(String title, String link, Feed feed) {
        this.title = title;
        this.link = link;
        this.feed = feed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

        public LocalDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

        public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }   

    public Set<Collection> getCollections() { return collections; }
    public void setCollections(Set<Collection> collections) { this.collections = collections; }

}
