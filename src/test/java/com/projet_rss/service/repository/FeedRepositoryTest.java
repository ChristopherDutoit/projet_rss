package com.projet_rss.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.projet_rss.domain.Feed;
import com.projet_rss.repository.FeedRepository;

@DataJpaTest
public class FeedRepositoryTest {

    @Autowired
    private FeedRepository feedRepository;

     @Test
     void shouldSaveAndRetrieveFeed() {

        Feed myFeed = new Feed("Hacker News", "https://news.ycombinator.com/rss");

        Feed savedFeed = feedRepository.save(myFeed);

        assertNotNull(savedFeed.getId(), "L'ID ne doit pas être null après la sauvegarde");
        
        assertEquals(1, feedRepository.findAll().size(), "Il doit y avoir exactement 1 flux en base");
     }

}
