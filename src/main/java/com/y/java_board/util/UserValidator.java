package com.y.java_board.util;

import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(\\S+)$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z]).{5,}$");

    public static void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("[이메일 주소 오류] 이메일 주소 형식에 맞지 않습니다.");
        }
    }

    public static void validatePassword(String password){
        if(!PASSWORD_PATTERN.matcher(password).matches()){
            throw new IllegalArgumentException("[비밀번호 오류] 비밀번호는 영어 1자 포함, 전체길이 5글자입니다.");
        }
    }

}
