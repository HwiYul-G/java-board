package com.y.java_board.service;

import com.y.java_board.domain.Article;
import com.y.java_board.domain.Comment;
import com.y.java_board.dto.CommentDto;
import com.y.java_board.repository.ArticleRepository;
import com.y.java_board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    public Comment createOne(CommentDto dto, Long articleId) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if (articleOptional.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Comment comment = Comment.createComment(dto, articleOptional.get());
        return commentRepository.save(comment);
    }

    public List<Comment> findCommentsByArticleId(long articleId) {
        return commentRepository.findByArticleId(articleId);
    }
}