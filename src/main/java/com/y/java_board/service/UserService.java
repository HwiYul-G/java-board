package com.y.java_board.service;


import com.y.java_board.domain.User;
import com.y.java_board.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User register(User user) throws IllegalStateException {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("[중복 이메일] 이미 가입된 이메일 입니다.");
                });
        userRepository.findByNickname(user.getNickname())
                .ifPresent(u -> {
                    throw new IllegalStateException("[중복 닉네임] 이미 존재하는 닉네임 입니다.");
                });
        return userRepository.save(user);
    }

    public String getNicknameByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(User::getNickname)
                .orElseThrow(() -> new IllegalArgumentException("[존재하지 않는 이메일] 해당 이메일의 사용자를 찾을 수 없습니다."));
    }


//    public User updateUser(){
//
//    }
//
//    public void deleteUser(Long id){
//
//    }


}
