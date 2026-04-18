package com.projet_rss.web;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.projet_rss.domain.Article;
import com.projet_rss.domain.Collection; 
import com.projet_rss.repository.CollectionRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/collections")
public class CollectionResource {

    private final CollectionRepository collectionRepository;

    public CollectionResource(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Collection create(@RequestBody Collection collection) {
        return collectionRepository.save(collection);
    }

    @PutMapping("/{id}")
    public Collection update(@PathVariable UUID id, @RequestBody Collection collectionDetails) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        collection.setName(collectionDetails.getName());
        return collectionRepository.save(collection);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        collectionRepository.deleteById(id);
    }

    @GetMapping
    public List<Collection> getCollections() {
        return collectionRepository.findAll();
    }

    @GetMapping("/{id}/articles")
        public Set<Article> getArticlesByCollection(@PathVariable UUID id) {
        Collection collection = collectionRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection non trouvée"));
            
        return collection.getArticles();
}
    
}