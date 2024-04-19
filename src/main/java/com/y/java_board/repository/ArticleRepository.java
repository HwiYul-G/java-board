package com.y.java_board.repository;

import com.y.java_board.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);
    Optional<Article> findById(Long id);
    List<Article> findByWriter(String writer);
    List<Article> findAll();
    void deleteById(Long id);
}
