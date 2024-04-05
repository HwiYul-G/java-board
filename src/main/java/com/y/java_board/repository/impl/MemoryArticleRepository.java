package com.y.java_board.repository.impl;

import com.y.java_board.domain.Article;
import com.y.java_board.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Map<Long, Article> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public Article save(Article article) {
        if(article.getId() == null) {
            article.setId(++sequence);
            store.put(article.getId(), article);
        }else{
            store.put(article.getId(), article);
        }
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    public void clearStore(){
        store.clear();
    }

}
