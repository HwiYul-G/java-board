package com.y.java_board.controller;

import com.y.java_board.domain.User;
import com.y.java_board.dto.UserDto;
import com.y.java_board.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.service = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user/register")
    public String showSignUpForm(ModelMap model){
        model.addAttribute("userDto", new User());
        return "user/signup";
    }

    @PostMapping("/user/register")
    public String signUp(UserDto userDto){
        try {
            User user = userDto.toEntity();
            user.setPassword(passwordEncoder.encode(userDto.password()));

            service.register(user);
        } catch (IllegalStateException e){
            // TODO : 오류 처리
        }
        return "redirect:/";
    }

}
