package com.y.java_board.service;

import com.y.java_board.domain.Article;
import com.y.java_board.domain.Comment;
import com.y.java_board.dto.CommentDto;
import com.y.java_board.repository.ArticleRepository;
import com.y.java_board.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.initMocks(this);

        // basic data
        // articleId가 1인 article
        // articleId가 2인 article에 기본 댓글이 3개(기본 댓글 아이디 1, 2, 3)
        // articleId가 3인 article에 기본 댓글이 2개(기본 댓글 아이디 4, 5)
        initData();
    }


    @Test
    void createOne_nonExistingArticleId_IllegalArgumentException(){
        //given
        CommentDto commentDto = new CommentDto("","테스트내용",20001L);
        // when & then
        assertThrows(IllegalArgumentException.class, () -> commentService.createOne(commentDto, commentDto.articleId()));
    }


    @Test
    void findCommentsByArticleId_validArticleId_commentsFound(){
        // given
        Long articleId = 2L;
        // when
        List<Comment> foundedComments = commentService.findCommentsByArticleId(articleId);
        // then
        int expected = 3;
        assertThat(foundedComments.size()).isEqualTo(expected);
    }

    @Test
    void deleteComment_validCommentId_commentDeleted(){
        // given
        Long commentId = 1L;
        // when
        commentService.deleteComment(commentId);
        // then
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void updateComment_nonExistingComment_IllegalArgumentException(){
        // given
        Long commentId = 100L;
        // when & then
        assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(commentId, "Hello"));
    }

    private void initData(){
        Article article1 = new Article();
        article1.setId(1L);

        Article article2 = new Article();
        article2.setId(2L);
        List<Comment> commentsForArticle2 = IntStream.rangeClosed(1, 3)
                .mapToObj(i ->{
                    Comment comment = new Comment();
                    comment.setId((long) i);
                    comment.setArticle(article2);
                    return comment;
                })
                .collect(Collectors.toList());

        Article article3 = new Article();
        article3.setId(3L);
        List<Comment> commentsForArticle3 = IntStream.rangeClosed(4, 5)
                .mapToObj(i -> {
                    Comment comment = new Comment();
                    comment.setId((long) i);
                    comment.setArticle(article3);
                    return comment;
                })
                .collect(Collectors.toList());

        // Mock 설정
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article1));
        when(articleRepository.findById(2L)).thenReturn(Optional.of(article2));
        when(articleRepository.findById(3L)).thenReturn(Optional.of(article3));
        when(commentRepository.findByArticleId(2L)).thenReturn(commentsForArticle2);
        when(commentRepository.findByArticleId(3L)).thenReturn(commentsForArticle3);
    }




}