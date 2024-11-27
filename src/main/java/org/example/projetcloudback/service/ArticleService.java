package org.example.projetcloudback.service;

import org.example.projetcloudback.model.Article;
import org.example.projetcloudback.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public Article getArticleById(UUID id) {
        return articleRepository.findById(id).orElse(null);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public void deleteArticleById(UUID id) {
        articleRepository.deleteById(id);
    }

    public void updateArticle(Article article) {articleRepository.save(article);}
}
