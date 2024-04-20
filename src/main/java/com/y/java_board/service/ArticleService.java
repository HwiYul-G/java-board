package com.y.java_board.service;

import com.y.java_board.domain.Article;
import com.y.java_board.repository.ArticleRepository;
import com.y.java_board.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;


    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> findOne(Long id) {
        return articleRepository.findById(id);
    }

    public Long createOne(Article article) {
        // 중복 검사, null 검사 등 수행
        articleRepository.save(article);
        return article.getId();
    }

    @Transactional
    public void deleteOne(Long id) {
        commentRepository.deleteByArticleId(id);
        articleRepository.findById(id)
                .ifPresent(article -> articleRepository.deleteById(id));
    }

    public Long updateOne(Article article) {
        Article origin = articleRepository.findById(article.getId())
                .orElseThrow(() -> new NoSuchElementException("The id doesn't exist!"));
        origin.patch(article);
        articleRepository.save(origin);
        return origin.getId();
    }


}
