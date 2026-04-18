package com.projet_rss.web;

import com.projet_rss.domain.Article;
import com.projet_rss.domain.Collection;
import com.projet_rss.repository.ArticleRepository;
import com.projet_rss.repository.CollectionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleResource {

    private final ArticleRepository articleRepository;
    private final CollectionRepository collectionRepository;

    public ArticleResource(ArticleRepository articleRepository, CollectionRepository collectionRepository) {
        this.articleRepository = articleRepository;
        this.collectionRepository = collectionRepository;
    }

    @GetMapping("/articles")
    public List<Article> getAllArticles(@RequestParam(required = false) UUID feedId) {
       
        if (feedId != null) {
        return articleRepository.findAllByFeedIdOrderByCreatedAtDesc(feedId);
        }

        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    @PatchMapping("/articles/{id}/read")
    public void markAsRead(@PathVariable UUID id) {
    
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article non trouvé"));

    article.setRead(true);
    articleRepository.save(article);
    }

    @PostMapping("/{articleId}/collections/{collectionId}")
    public void addArticleToCollection(@PathVariable UUID articleId, @PathVariable UUID collectionId) {
    Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
        Collection collection = collectionRepository.findById(collectionId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        
        article.getCollections().add(collection);
    
        articleRepository.save(article);
}
}