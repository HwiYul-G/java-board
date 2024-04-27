package com.y.java_board.repository;

import com.y.java_board.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    void deleteById(Long id);
}
