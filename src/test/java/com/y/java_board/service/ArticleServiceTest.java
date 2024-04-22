package com.y.java_board.service;

import com.y.java_board.domain.Article;
import com.y.java_board.repository.impl.MemoryArticleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleServiceTest {

    ArticleService articleService;
    MemoryArticleRepository memoryArticleRepository;


    @BeforeEach
    public void beforeEach(){
        // 임시적으로..
        memoryArticleRepository = new MemoryArticleRepository();
        articleService = new ArticleService(memoryArticleRepository, null, null);
    }

    @AfterEach
    public void afterEach(){
        memoryArticleRepository.clearStore();
    }

    @Test
    void updateOne_nonExistingId_NoSuchElementException(){
        Article article = new Article("테스트제목1", "테스트내용1","테스트저자1");
        article.setId(100L);

        assertThatThrownBy(() -> articleService.updateOne(article)).isInstanceOf(NoSuchElementException.class);
    }

}