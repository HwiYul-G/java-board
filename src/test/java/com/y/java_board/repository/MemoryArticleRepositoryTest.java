package com.y.java_board.repository;

import com.y.java_board.domain.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository repository = new MemoryArticleRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Article article = new Article("테스트제목", "테스트내용", "저자");

        repository.save(article);

        Article result = repository.findById(article.getId()).get();
        assertThat(article).isEqualTo(result);
    }

    @Test
    public void findAll(){
        int expected = 3;

        repository.save(new Article("제1","내1","저1"));
        repository.save(new Article("제2","내2","저2"));
        repository.save(new Article("제3","내3","저3"));

        List<Article> result = repository.findAll();

        assertThat(result.size()).isEqualTo(expected);
    }

    @Test
    public void deleteById(){
        boolean expected = false;

        repository.save(new Article("제1","내1","저1"));
        repository.save(new Article("제2","내2","저2"));
        repository.save(new Article("제3","내3","저3"));

        repository.deleteById(1L);
        Optional<Article> result = repository.findById(1L);

        assertThat(result.isPresent()).isEqualTo(expected);
    }


}