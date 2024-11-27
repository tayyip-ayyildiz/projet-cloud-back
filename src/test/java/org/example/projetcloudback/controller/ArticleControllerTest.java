package org.example.projetcloudback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.projetcloudback.model.Article;
import org.example.projetcloudback.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleService articleService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    void getArticleById_ReturnsOk_WhenArticleExists() throws Exception {
        UUID id = UUID.randomUUID();
        Article article = new Article(id, "Test Article", "Description", "image.png");
        when(articleService.getArticleById(id)).thenReturn(article);

        mockMvc.perform(get("/articles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void getArticleById_ReturnsNotFound_WhenArticleDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(articleService.getArticleById(id)).thenReturn(null);

        mockMvc.perform(get("/article/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getArticles_ReturnsOkWithList() throws Exception {
        List<Article> articles = Arrays.asList(
                new Article(UUID.randomUUID(), "Article 1", "Description", "image1.png"),
                new Article(UUID.randomUUID(), "Article 2", "Description", "image2.png")
        );
        when(articleService.getAllArticles()).thenReturn(articles);

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void createArticle_ReturnsCreated() throws Exception {
        Article article = new Article(null, "New Article", "Description", "image.png");
        String json = objectMapper.writeValueAsString(article);

        mockMvc.perform(post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(articleService).createArticle(Mockito.any(Article.class));
    }

    @Test
    void deleteArticle_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(articleService).deleteArticleById(id);

        mockMvc.perform(delete("/article/{id}", id))
                .andExpect(status().isNoContent());

        verify(articleService).deleteArticleById(id);
    }

    // marche pas à regler
    @Test
    void updateArticle_ReturnsNoContent_WhenArticleIsUpdated() throws Exception {
        UUID id = UUID.randomUUID();
        Article existingArticle = new Article(id, "Old Article", "Old Description", "oldImage.png");
        Article articleToUpdate = new Article(id, "Updated Article", "Updated Description", "updatedImage.png");

        // Simuler l'existence de l'article
        when(articleService.getArticleById(id)).thenReturn(existingArticle);

        // Simuler la mise à jour de l'article
        doNothing().when(articleService).updateArticle(Mockito.any(Article.class));

        // Effectuer la requête PUT avec les données mises à jour
        mockMvc.perform(MockMvcRequestBuilders.put("/article/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)  // Set content type to JSON
                        .content("{\"name\": \"Updated Article\", \"description\": \"Updated Description\", \"image\": \"updatedImage.png\"}"))
                .andExpect(status().isNoContent());  // Vérifier que la réponse est 204 No Content
    }


}

