package com.y.java_board.dto;

import com.y.java_board.domain.Article;

public record ArticleDto(String title, String content, String writer) {

    public Article toEntity() {
        return new Article(title, content, writer);
    }
}
