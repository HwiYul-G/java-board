package com.y.java_board.service;

import com.y.java_board.domain.User;
import com.y.java_board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void givenNewUser_whenRegister_thenSuccess(){
        User newUser = new User("can@google.com", "password", "testname", "testnickname");
        when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);

        User savedUser = userService.register(newUser);

        Assertions.assertThat(savedUser.getId()).isEqualTo(newUser.getId());
    }

    @Test
    void givenDuplicatedEmailUser_whenRegister_thenIllegalStateException(){
        User existingUser = new User("existing@google.com", "password", "testname", "testnickname");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        User newUser = new User("existing@google.com", "password", "testname1", "testnickname2");

        Assertions.assertThatThrownBy(() -> userService.register(newUser))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[중복 이메일] 이미 가입된 이메일 입니다.");
    }
}