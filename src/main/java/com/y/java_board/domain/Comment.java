package com.y.java_board.domain;

import com.y.java_board.dto.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private Article article;

    public Comment() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    public Comment(String writer, String content, Article article) {
        this.writer = writer;
        this.content = content;
        this.article = article;
        createdAt = updatedAt = LocalDateTime.now();
    }

    public static Comment createComment(CommentDto commentDto, Article article) {
        return new Comment(commentDto.writer(), commentDto.content(), article);
    }

}
