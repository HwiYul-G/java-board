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
    void givenDuplicatedEmailUser_whenRegister_thenIllegalStateException(){
        User existingUser = new User("existing@google.com", "password", "testname", "testnickname");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        UserDto newUserDto = new UserDto("existing@google.com", "password", "testname1", "testnickname2");

        Assertions.assertThatThrownBy(() -> userService.registerNewUserAccount(newUserDto))
                .isInstanceOf(IllegalArgumentException.class);
    }
}