package com.y.java_board.controller;

import com.y.java_board.domain.Comment;
import com.y.java_board.dto.CommentDto;
import com.y.java_board.service.CommentService;
import com.y.java_board.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/articles/{articleId}/comments")
    public String create(@PathVariable Long articleId, @RequestParam String email, CommentDto dto) {
        String nickname = userService.getNicknameByEmail(email);
        CommentDto commentDto = new CommentDto(nickname, dto.content(), articleId);
        Comment createdComment = commentService.createOne(commentDto, articleId);
        return "redirect:/articles/{articleId}";
    }

    @GetMapping("/articles/{articleId}/comments/delete/{id}")
    public String delete(@PathVariable Long id, @PathVariable Long articleId){
        commentService.deleteComment(id);
        return "redirect:/articles/{articleId}";
    }


    @PostMapping("/articles/{articleId}/comments/update/{id}")
    public String update(@PathVariable Long articleId, @PathVariable Long id, String content){
        commentService.updateComment(id, content);
        return "redirect:/articles/{articleId}";
    }

}
