package com.y.java_board.service;

import com.y.java_board.domain.User;
import com.y.java_board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User register(User user) throws IllegalStateException {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent())
            throw new IllegalStateException("[중복 이메일] 이미 가입된 이메일 입니다.");
        return userRepository.save(user);
    }

//    public User updateUser(){
//
//    }
//
//    public void deleteUser(Long id){
//
//    }


}
