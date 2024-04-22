package com.y.java_board.repository.impl;

import com.y.java_board.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagingArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Page<Article> findByWriter(String nickname, Pageable pageable);
}
