package com.y.java_board.dto;

import com.y.java_board.domain.User;

import java.util.regex.Pattern;

public record UserDto(String email, String password, String name, String nickname) {

    public User toEntity() {
        return new User(email, password, name, nickname);
    }

}
