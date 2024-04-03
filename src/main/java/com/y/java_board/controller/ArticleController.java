package com.y.java_board.controller;

import com.y.java_board.domain.Article;
import com.y.java_board.dto.ArticleDto;
import com.y.java_board.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller()
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String articles(Model model){
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        return "article/articles";
    }

    @GetMapping("/articles/new")
    public String showCreateForm(Model model){
        model.addAttribute("articleDto", new ArticleDto("","",""));
        return "article/create";
    }

    @PostMapping("/articles/new")
    public String create(ArticleDto articleDto, BindingResult result, Model model){
        if(result.hasErrors()){
            // model.addAttribute() 오류의 원인을 보낸다.
            return "/articles/new";
        }
        articleService.createOne(articleDto.toEntity());
        return "redirect:/articles";
    }



}
