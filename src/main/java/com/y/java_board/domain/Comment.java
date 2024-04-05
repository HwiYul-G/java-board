package com.y.java_board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private Article article;

    public Comment(){
        createdAt = updatedAt = LocalDateTime.now();
    }

    public Comment(String writer, String content, Article article){
        this.writer = writer;
        this.content = content;
        this.article = article;
        createdAt = updatedAt = LocalDateTime.now();
    }

}
