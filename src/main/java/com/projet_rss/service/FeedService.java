package com.projet_rss.service;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import com.projet_rss.domain.Article;
import com.projet_rss.domain.Feed;
import com.projet_rss.repository.ArticleRepository;
import com.projet_rss.repository.FeedRepository;

import jakarta.transaction.Transactional;

@Service
public class FeedService {
    
    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);
    
    private final FeedParserService parserService;
    private final FeedRepository feedRepository;
    private final ArticleRepository articleRepository;

    public FeedService(FeedParserService parserService, FeedRepository feedRepository, ArticleRepository articleRepository) {
        this.parserService = parserService;
        this.feedRepository = feedRepository;
        this.articleRepository = articleRepository;
    }

    public Feed subscribeFeed(String url) {
        Feed newFeed = parserService.parseFeed(url);

        newFeed.setUrl(url);

        return feedRepository.save(newFeed);
    }

    @Transactional
    public void addArticlesToFeed(UUID feedId, List<Article> articles) {
    Feed feed = feedRepository.findById(feedId)
        .orElseThrow(() -> new RuntimeException("Feed introuvable : " + feedId));

    for (Article article : articles) {
        if (!articleRepository.existsByLink(article.getLink())) {
            article.setFeed(feed); 
            articleRepository.save(article);
        }
    }
}
    //@Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "0 0 * * * *") 
    public void syncAllFeeds() {
    logger.info("Début de la synchronisation automatique des flux");
    List<Feed> feeds = feedRepository.findAll();
    
    for (Feed feed : feeds) {
        try {

            List<Article> fetchedArticles = parserService.parseArticles(feed.getUrl())
                .stream()
                .limit(50)
                .toList();
            
            this.addArticlesToFeed(feed.getId(), fetchedArticles);
            
            logger.info("Flux synchronisé : {}", feed.getTitle());
        } catch (Exception e) {
            logger.error("Erreur lors de la synchro du flux {}", feed.getTitle(), e);
        }
    }
}

}
