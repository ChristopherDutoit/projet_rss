package com.projet_rss.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.projet_rss.domain.Article;
import com.projet_rss.domain.Feed;
import com.projet_rss.repository.ArticleRepository;
import com.projet_rss.repository.FeedRepository;

import jakarta.transaction.Transactional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ArticleResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired 
    private ArticleRepository articleRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Test
    void shouldGetAllArticles() throws Exception {
        mockMvc.perform(get("/api/articles")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldMarkArticleAsRead() throws Exception {
        Article article = new Article("Titre Test", "https://test.com/read-me", null);
        article = articleRepository.save(article);
        UUID articleId = article.getId();

     
        mockMvc.perform(patch("/api/articles/" + articleId + "/read"))
               .andExpect(status().isOk());

        Article updatedArticle = articleRepository.findById(articleId).orElseThrow();
        assertThat(updatedArticle.isRead()).isTrue();
    }

    @Test
    void shouldFilterArticlesByFeed() throws Exception {
        Feed feedA = feedRepository.save(new Feed("Flux A", "https://a.com"));
        Feed feedB = feedRepository.save(new Feed("Flux B", "https://b.com"));
    
        articleRepository.save(new Article("News A", "https://a.com/1", feedA));
        articleRepository.save(new Article("News B", "https://b.com/1", feedB));

        mockMvc.perform(get("/api/articles")
            .param("feedId", feedA.getId().toString())) 
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1)) 
            .andExpect(jsonPath("$[0].title").value("News A"));
}
}