package com.y.java_board.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
@AllArgsConstructor
public class UserInfoSession {
    private String email;
    private String nickname;
    private String profileImage;
    private String name;
}
