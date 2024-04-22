package com.y.java_board.controller;

import com.y.java_board.config.UserInfoSession;
import com.y.java_board.dto.CommentDto;
import com.y.java_board.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@SessionAttributes("userInfo")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/articles/{articleId}/comments")
    public String create(@PathVariable Long articleId, @ModelAttribute("userInfo")UserInfoSession userInfoSession, CommentDto dto) {
        CommentDto commentDto = new CommentDto(userInfoSession.getNickname(), dto.content(), articleId);
        commentService.createOne(commentDto, articleId);
        return "redirect:/articles/detail/{articleId}";
    }

    @DeleteMapping("/articles/{articleId}/comments/{id}")
    public String delete(@PathVariable Long id, @PathVariable Long articleId){
        commentService.deleteComment(id);
        return "redirect:/articles/detail/{articleId}";
    }


    @PutMapping("/articles/{articleId}/comments/{id}")
    public String update(@PathVariable Long articleId, @PathVariable Long id, String content){
        commentService.updateComment(id, content);
        return "redirect:/articles/detail/{articleId}";
    }

}
