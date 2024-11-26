package org.example.projetcloudback.controller;

import org.example.projetcloudback.model.Article;
import org.example.projetcloudback.repository.ArticleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ArticleController {


    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles")
    public List<Article> getArticles(@RequestParam(required = false) String filter) {
        if (filter != null) {
            return articleRepository.findAll()
                    .stream()
                    .filter(article -> article.getName().contains(filter))
                    .toList();
        }
        return articleRepository.findAll();
    }
}
