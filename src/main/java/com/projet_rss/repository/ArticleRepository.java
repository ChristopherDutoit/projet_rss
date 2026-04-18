package com.projet_rss.repository;

import com.projet_rss.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    
    @Modifying
    @Query("DELETE FROM Article a WHERE a.createdAt < :date AND a.collections IS EMPTY")
    void deleteOldUncollectedArticles(Instant date);
    
    List<Article> findAllByFeedIdOrderByCreatedAtDesc(UUID feedId);
    
    List<Article> findAllByOrderByCreatedAtDesc();
    
    boolean existsByLink(String link);
}