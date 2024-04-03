package com.y.java_board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Article(){
        createdAt = updatedAt = LocalDateTime.now();
    }

    public Article(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    public void patch(Article article){
        this.title = article.title;
        this.content = article.content;
        this.writer = article.writer;
        this.updatedAt = LocalDateTime.now();
    }

}
