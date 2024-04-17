package com.y.java_board.repository;

import com.y.java_board.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findByArticleId(Long articleId);
    void deleteById(Long id);
    void deleteByArticleId(Long articleId);

}
