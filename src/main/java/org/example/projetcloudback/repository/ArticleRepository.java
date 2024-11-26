package org.example.projetcloudback.repository;

import org.example.projetcloudback.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
