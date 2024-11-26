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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {

    @InjectMocks
    private ArticleController articleController;  // Inject mocks directly into the controller

    @Mock
    private ArticleService articleService;  // Mock the service

    private MockMvc mockMvc; // To mock the HTTP requests

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getArticleById_ReturnsArticle_WhenArticleExists() throws Exception {
        UUID id = UUID.randomUUID();
        Article mockArticle = new Article(id, "Article 1", "Description", "image1.png");
        Mockito.when(articleService.getArticleById(id)).thenReturn(mockArticle);

        mockMvc.perform(get("/articles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Article 1"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.image").value("image1.png"));
    }

    @Test
    void getArticleById_ReturnsNotFound_WhenArticleDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(articleService.getArticleById(id)).thenReturn(null);

        mockMvc.perform(get("/articles/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getArticles_ReturnsListOfArticles() throws Exception {
        List<Article> mockArticles = Arrays.asList(
                new Article(UUID.randomUUID(), "Article 1", "Description", "image1.png"),
                new Article(UUID.randomUUID(), "Article 2", "Description", "image2.png")
        );
        Mockito.when(articleService.getAllArticles()).thenReturn(mockArticles);

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Article 1"))
                .andExpect(jsonPath("$[1].name").value("Article 2"));
    }

    @Test
    void createArticle_CallsCreateArticle() throws Exception {
        Article newArticle = new Article(null, "Article 3", "Description", "image3.png");
        String articleJson = objectMapper.writeValueAsString(newArticle);

        mockMvc.perform(post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleJson))
                .andExpect(status().isOk());

        verify(articleService, times(1)).createArticle(Mockito.any(Article.class));
    }

    @Test
    void deleteArticle_CallsDeleteMethod() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/article/{id}", id))
                .andExpect(status().isOk());

        verify(articleService, times(1)).deleteArticleById(id);
    }

    // Setup MockMvc
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }
}
