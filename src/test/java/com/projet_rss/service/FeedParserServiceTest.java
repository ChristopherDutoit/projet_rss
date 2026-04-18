package com.projet_rss.service;

import com.projet_rss.domain.Feed;
import com.projet_rss.service.FeedParserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeedParserServiceTest {

    @Test
    void shouldExtractTitleFromValidRssUrl(){

        FeedParserService parserService = new FeedParserService();
        String validUrl = "https://news.ycombinator.com/rss";

        Feed result = parserService.parseFeed(validUrl);

        assertNotNull(result, "L'objet Feed ne doit pas être null");
        assertEquals("Hacker News", result.getTitle(), "Le titre extrait du flux doit être correct");
    }
}