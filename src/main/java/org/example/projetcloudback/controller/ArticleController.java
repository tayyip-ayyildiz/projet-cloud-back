package org.example.projetcloudback.controller;

import org.example.projetcloudback.model.Article;
import org.example.projetcloudback.repository.ArticleRepository;
import org.example.projetcloudback.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<Article> getArticles(@RequestParam(required = false) String filter) {
        return articleService.getAllArticles();
    }

    @DeleteMapping("/article/{id}")
    public void deleteArticle(@PathVariable(required = true) UUID id) {
        articleService.deleteArticleById(id);
    }

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable(required = true) UUID id) {
        return articleService.getArticleById(id);
    }

    @PostMapping("/article")
    public void createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
    }


}
