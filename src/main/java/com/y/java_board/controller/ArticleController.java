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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable("id") long id, Model model){
        Optional<Article> articleOptional = articleService.findOne(id);
        if(articleOptional.isPresent()){
            model.addAttribute("article",articleOptional.get());
            return "/article/detail";
        }
        // TODO : 해당 id 가 없다는 안내가 필요할 것 같다.
        return "/articles";
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable("id") long id, Model model){
        Article article = articleService.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid article Id : " + id));
        articleService.deleteOne(id);
        return "redirect:/articles";
    }

    @GetMapping("/articles/update/{id}")
    public String showUpdateForm(@PathVariable long id, Model model){
        Article article = articleService.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid article Id: "+ id));
        model.addAttribute("articleDto", new ArticleDto(article.getTitle(),article.getContent(), article.getWriter()));
        return "/article/update";
    }

    @PostMapping("/articles/update/{id}")
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
