package com.y.java_board.repository;

import com.y.java_board.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface SpringDataJpaArticleRepository extends JpaRepository<Article,Long>, ArticleRepository {

}
