package com.projet_rss.web;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;  
import com.jayway.jsonpath.JsonPath;
import com.projet_rss.repository.CollectionRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import jakarta.transaction.Transactional;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CollectionResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CollectionRepository collectionRepository;

    @Test
    void shouldCreateRenameAndDeleteCollection() throws Exception {
    
        String json = "{\"name\": \"Ma Veille\"}";
        String response = mockMvc.perform(post("/api/collections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print()) 
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        UUID id = UUID.fromString(JsonPath.read(response, "$.id"));

        mockMvc.perform(put("/api/collections/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Veille Tech\"}"))
                .andExpect(status().isOk());

        assertThat(collectionRepository.findById(id).get().getName()).isEqualTo("Veille Tech");

        mockMvc.perform(delete("/api/collections/" + id))
                .andExpect(status().isNoContent());

        assertThat(collectionRepository.findById(id)).isEmpty();
    }
}