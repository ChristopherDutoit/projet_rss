package com.projet_rss.service;

import org.springframework.stereotype.Service;

import com.projet_rss.domain.Feed;
import com.projet_rss.repository.FeedRepository;

@Service
public class FeedService {
    private final FeedParserService parserService;
    private final FeedRepository feedRepository;

    public FeedService(FeedParserService parserService, FeedRepository feedRepository) {
        this.parserService = parserService;
        this.feedRepository = feedRepository;
    }

    public Feed subscribeFeed(String url) {
        Feed newFeed = parserService.parseFeed(url);

        newFeed.setUrl(url);

        return feedRepository.save(newFeed);
    }

}
