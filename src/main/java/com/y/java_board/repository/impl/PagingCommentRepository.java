package com.y.java_board.repository.impl;

import com.y.java_board.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagingCommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findByWriter(String nickname, Pageable pageable);
}
