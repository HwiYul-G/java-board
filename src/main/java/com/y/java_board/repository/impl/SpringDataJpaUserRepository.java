package com.y.java_board.repository.impl;

import com.y.java_board.domain.User;
import com.y.java_board.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaUserRepository extends UserRepository, JpaRepository<User, Long> {

}
