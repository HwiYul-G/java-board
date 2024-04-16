package com.y.java_board.controller;

import com.y.java_board.domain.Comment;
import com.y.java_board.dto.CommentDto;
import com.y.java_board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public String create(
            @PathVariable Long articleId, CommentDto dto) {
        Comment createdComment = commentService.createOne(dto, articleId);
        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/articles/{articleId}/comments/delete/{id}")
    public String delete(@PathVariable Long id, @PathVariable Long articleId){
        commentService.deleteComment(id);
        return "redirect:/articles/{articleId}";
    }


    @PutMapping("/articles/{articleId}/comments/update/{id}")
    public String update(@PathVariable Long articleId, @PathVariable Long id, String content){
        commentService.updateComment(id, content);
        return "redirect:/articles/{articleId}";
    }

}
