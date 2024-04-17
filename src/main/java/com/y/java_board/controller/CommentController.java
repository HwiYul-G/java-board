package com.y.java_board.controller;

import com.y.java_board.domain.Comment;
import com.y.java_board.dto.CommentDto;
import com.y.java_board.dto.UserDto;
import com.y.java_board.service.CommentService;
import com.y.java_board.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@SessionAttributes("loggedInUser")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/articles/{articleId}/comments")
    public String create(@PathVariable Long articleId, @ModelAttribute("loggedInUser")UserDto loggedInUser, CommentDto dto) {
        CommentDto commentDto = new CommentDto(loggedInUser.nickname(), dto.content(), articleId);
        Comment createdComment = commentService.createOne(commentDto, articleId);
        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/articles/{articleId}/comments/{id}")
    public String delete(@PathVariable Long id, @PathVariable Long articleId){
        commentService.deleteComment(id);
        return "redirect:/articles/{articleId}";
    }


    @PutMapping("/articles/{articleId}/comments/{id}")
    public String update(@PathVariable Long articleId, @PathVariable Long id, String content){
        commentService.updateComment(id, content);
        return "redirect:/articles/{articleId}";
    }

}
