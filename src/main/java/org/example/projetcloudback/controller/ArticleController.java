package org.example.projetcloudback.controller;

import org.example.projetcloudback.model.Article;
import org.example.projetcloudback.repository.ArticleRepository;
import org.example.projetcloudback.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getArticles(@RequestParam(required = false) String filter) {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable UUID id) {
        if (id != null) {
            articleService.deleteArticleById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable UUID id) {
        Article article = articleService.getArticleById(id);
        if (article != null) {
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/article")
    public ResponseEntity<Void> createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable UUID id, @RequestBody Article article) {
        // Vérification si l'article existe avant de le mettre à jour
        Article existingArticle = articleService.getArticleById(id);
        if (existingArticle == null) {
            // Si l'article n'existe pas, renvoyer un 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Mettez à jour l'article avec les données fournies
        existingArticle.setName(article.getName());
        existingArticle.setDescription(article.getDescription());
        existingArticle.setImage(article.getImage());

        // Appel à la logique du service pour effectuer la mise à jour
        articleService.updateArticle(existingArticle);

        // Retourner l'article mis à jour avec un 200 OK
        return ResponseEntity.ok(existingArticle);
    }


}
