package com.projet_rss.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.projet_rss.repository.ArticleRepository;

import jakarta.transaction.Transactional;

@Service
public class CleanupService {

    private final ArticleRepository articleRepository;

    public CleanupService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // On lance le nettoyage tous les jours à 3h du matin
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanup() {
        Instant threshold = Instant.now().minus(30, ChronoUnit.DAYS);
        articleRepository.deleteOldUncollectedArticles(threshold);
    }
}
