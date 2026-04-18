package com.projet_rss.repository;

import com.projet_rss.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    List<Article> findAllByFeedIdOrderByCreatedAtDesc(UUID feedId);
    List<Article> findAllByOrderByCreatedAtDesc();
    boolean existsByLink(String link);
}