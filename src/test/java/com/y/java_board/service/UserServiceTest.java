package com.y.java_board.service;

import com.y.java_board.domain.User;
import com.y.java_board.dto.UserDto;
import com.y.java_board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void givenNewUser_whenRegister_thenSuccess(){
        UserDto userDto = new UserDto("can@google.com","password","testname","testanickname");
        when(userRepository.save(Mockito.any(User.class))).thenReturn(userDto.toEntity());

        User savedUser = userService.registerNewUserAccount(userDto);

        Assertions.assertThat(savedUser.getEmail()).isEqualTo(userDto.email());
    }

    @Test
    void givenDuplicatedEmailUser_whenRegister_thenIllegalStateException(){
        User existingUser = new User("existing@google.com", "password", "testname", "testnickname");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        UserDto newUserDto = new UserDto("existing@google.com", "password", "testname1", "testnickname2");

        Assertions.assertThatThrownBy(() -> userService.registerNewUserAccount(newUserDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[중복 이메일] 이미 가입된 이메일 입니다.");
    }
}