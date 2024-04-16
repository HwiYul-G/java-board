package com.y.java_board.controller;

import com.y.java_board.domain.Article;
import com.y.java_board.domain.Comment;
import com.y.java_board.dto.ArticleDto;
import com.y.java_board.dto.CommentDto;
import com.y.java_board.service.ArticleService;
import com.y.java_board.service.CommentService;
import com.y.java_board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/articles")
    public String articles(Model model){
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "article/articles";
    }

    @GetMapping("/articles/new")
    public String showCreateForm(Model model, @AuthenticationPrincipal UserDetails userDetails){
        String nickname = userService.getNicknameByEmail(userDetails.getUsername());
        model.addAttribute("articleDto", new ArticleDto("","", nickname));
        return "article/create";
    }

    @PostMapping("/articles")
    public String create(ArticleDto articleDto, BindingResult result, Model model){
        if(result.hasErrors()){
            // model.addAttribute() 오류의 원인을 보낸다.
            return "/articles/new";
        }
        articleService.createOne(articleDto.toEntity());
        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable("id") long id, Model model, Principal principal, HttpServletRequest request){
        Optional<Article> articleOptional = articleService.findOne(id);
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(articleOptional.isPresent()){
            List<Comment> comments = commentService.findCommentsByArticleId(id);
            model.addAttribute("article",articleOptional.get());
            String nickname = userService.getNicknameByEmail(principal.getName());
            model.addAttribute("commentDto", new CommentDto(nickname, "", id));
            model.addAttribute("comments", comments);
            if(inputFlashMap != null){
                model.addAttribute("auth", inputFlashMap.get("auth"));
            }
            return "/article/detail";
        }
        // TODO : 해당 id 가 없다는 안내가 필요할 것 같다.
        return "redirect:/articles";
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticle(@PathVariable("id") long id, Model model, Principal principal, RedirectAttributes redirectAttributes){
        Article article = articleService.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid article Id : " + id));
        String email = userService.getEmailByNickname(article.getWriter());
        if(!principal.getName().equals(email)){
            redirectAttributes.addFlashAttribute("auth", "삭제 권한이 없습니다.");
            return "redirect:/articles/{id}";
        }
        articleService.deleteOne(id);
        return "redirect:/articles";
    }

    @GetMapping("/articles/update/{id}")
    public String showUpdateForm(@PathVariable long id, Model model, Principal principal, RedirectAttributes redirectAttributes){
        Article article = articleService.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid article Id: "+ id));
        String email = userService.getEmailByNickname(article.getWriter());
        if(!principal.getName().equals(email)){
            redirectAttributes.addFlashAttribute("auth", "수정 권한이 없습니다.");
            return "redirect:/articles/{id}";
        }
        model.addAttribute("articleDto", new ArticleDto(article.getTitle(),article.getContent(), article.getWriter()));
        return "/article/update";
    }

    @PutMapping("/articles/{id}")
    public String updateArticle(@PathVariable("id") long id, ArticleDto articleDto, BindingResult result){
        if(result.hasErrors()){
            // TODO : 오류 메시지 등 처리
            return "/articles/{id}";
        }
        Article article = articleDto.toEntity();
        article.setId(id);

        articleService.updateOne(article);
        return "redirect:/articles/{id}";
    }

}
