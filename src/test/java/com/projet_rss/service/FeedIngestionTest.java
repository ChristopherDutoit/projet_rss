package com.projet_rss.service;

import com.projet_rss.domain.Article;
import com.projet_rss.domain.Feed;
import com.projet_rss.repository.ArticleRepository;
import com.projet_rss.repository.FeedRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedIngestionTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private FeedRepository feedRepository;

    @InjectMocks
    private FeedService feedService;

    @Test
    void shouldNotStoreDuplicateArticlesByLink() {
        // GIVEN
        UUID feedId = UUID.randomUUID();
        Feed mockFeed = new Feed("Test Feed", "http://test.com");
        
        // On simule : le feed existe bien en base
        when(feedRepository.findById(feedId)).thenReturn(Optional.of(mockFeed));
        
        String link = "http://unique-link.com";
        Article duplicate = new Article("Titre", link, null);

        when(articleRepository.existsByLink(link)).thenReturn(true);

        // WHEN
        feedService.addArticlesToFeed(feedId, List.of(duplicate));

        // THEN
        verify(articleRepository, never()).save(any(Article.class));
    }
}