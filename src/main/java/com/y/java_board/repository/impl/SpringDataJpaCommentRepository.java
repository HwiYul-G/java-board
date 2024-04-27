package com.y.java_board.repository.impl;

import com.y.java_board.domain.Comment;
import com.y.java_board.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaCommentRepository extends CommentRepository, JpaRepository<Comment, Long> {

}
