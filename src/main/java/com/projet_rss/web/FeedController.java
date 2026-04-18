package com.projet_rss.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet_rss.domain.Feed;
import com.projet_rss.service.FeedService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    
    @PostMapping
    public ResponseEntity<Feed> addFeed(@RequestBody UrlRequest request) {
        Feed savedFeed = feedService.subscribeFeed(request.url());
        return ResponseEntity.ok(savedFeed);
    }

    

    public record UrlRequest(String url) {}
}
