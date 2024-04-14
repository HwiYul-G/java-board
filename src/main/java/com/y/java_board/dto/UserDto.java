package com.y.java_board.dto;

import com.y.java_board.domain.User;

import java.util.regex.Pattern;

public record UserDto(String email, String password, String name, String nickname) {
    private static final Pattern emailPattern = Pattern.compile("^(.+)@(\\S+)$");

    public UserDto{
        if(email == null || password == null || name == null || nickname == null)
            throw new IllegalArgumentException("[정보 입력 오류] 회원 가입 정보는 들어가야 합니다!");
        validateEmail(email);
    }

    public User toEntity(){
        return new User(email, password, name, nickname);
    }

    private void validateEmail(String email){
        if(!emailPattern.matcher(email).matches()) {
            throw  new IllegalArgumentException("[이메일 주소 오류] 이메일 주소 형식에 맞지 않습니다.");
        }
    }

}
