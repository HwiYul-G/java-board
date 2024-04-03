package com.y.java_board.service;

import com.y.java_board.domain.Article;
import com.y.java_board.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> findOne(Long id) {
        return articleRepository.findById(id);
    }

    public Long createOne(Article article){
        // 중복 검사, null 검사 등 수행
        articleRepository.save(article);
        return article.getId();
    }

    public void deleteOne(Long id){
        articleRepository.findById(id)
                .ifPresent(article-> articleRepository.deleteById(id));
    }

    public Long updateOne(Article article){
        Article origin = articleRepository.findById(article.getId())
                .orElseThrow(()->new NoSuchElementException("The id doesn't exist!"));
        origin.patch(article);
        articleRepository.save(origin);
        return origin.getId();
    }




}
