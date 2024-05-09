package com.y.java_board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    public Article() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    public Article(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    public void patch(Article article) {
        this.title = article.title;
        this.content = article.content;
        this.writer = article.writer;
        this.updatedAt = LocalDateTime.now();
    }

}
