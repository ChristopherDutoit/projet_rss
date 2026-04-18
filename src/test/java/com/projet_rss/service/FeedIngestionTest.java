package com.projet_rss.service;

import com.projet_rss.domain.Article;

import com.projet_rss.repository.ArticleRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.UUID;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedIngestionTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private FeedService feedService;

    @Test
    void shouldNotStoreDuplicateArticlesByLink() {
        UUID feedId = UUID.randomUUID();
        String link = "http://unique-link.com";
        Article duplicate = new Article("Titre 1 Bis", link, null);

        when(articleRepository.existsByLink(link)).thenReturn(true);


        feedService.addArticlesToFeed(feedId, List.of(duplicate));

        verify(articleRepository, never()).save(any(Article.class));
    }
}