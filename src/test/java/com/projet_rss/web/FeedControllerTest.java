package com.projet_rss.web;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



import com.projet_rss.domain.Feed;
import com.projet_rss.service.FeedService;
import com.projet_rss.web.FeedController;

@WebMvcTest(FeedController.class)
class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedService feedService;

     @Test
     void shouldReturn200AndSavedFeedWhenPostingValidUrl() throws Exception {
        
        String urlRequest = "{\"url\": \"https://news.ycombinator.com/rss\"}";

        Feed mockFeed = new Feed("Hacker News", "https://news.ycombinator.com/rss");
        mockFeed.setId(UUID.randomUUID());

        Mockito.when(feedService.subscribeFeed("https://news.ycombinator.com/rss"))
               .thenReturn(mockFeed);

        mockMvc.perform(post("/api/feeds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(urlRequest))
                .andExpect(status().isOk()) // On veut un HTTP 200
                .andExpect(jsonPath("$.title").value("Hacker News")) // Le JSON de retour doit contenir le titre
                .andExpect(jsonPath("$.id").exists()); // Le JSON de retour doit contenir un ID
    }

}
