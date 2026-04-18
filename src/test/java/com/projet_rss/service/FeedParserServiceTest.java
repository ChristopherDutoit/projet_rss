package com.projet_rss.service;

import com.projet_rss.domain.Article;
import com.projet_rss.domain.Feed;
import com.projet_rss.service.FeedParserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

class FeedParserServiceTest {

    @Test
    void shouldExtractTitleFromValidRssUrl(){

        FeedParserService parserService = new FeedParserService();
        String validUrl = "https://news.ycombinator.com/rss";

        Feed result = parserService.parseFeed(validUrl);

        assertNotNull(result, "L'objet Feed ne doit pas être null");
        assertEquals("Hacker News", result.getTitle(), "Le titre extrait du flux doit être correct");
    }

    @Test
    void shouldParseArticlesFromRssFeed() {
        String url = "https://www.lemonde.fr/rss/une.xml";
        FeedParserService parserService = new FeedParserService();
      
        List<Article> articles = parserService.parseArticles(url);

        assertThat(articles).isNotEmpty();
        assertThat(articles.get(0).getTitle()).isNotBlank();
        assertThat(articles.get(0).getLink()).startsWith("http");
    }
}