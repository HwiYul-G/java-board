package com.y.java_board.config;

import com.y.java_board.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String email = authentication.getName();
            String nickname = userService.getNicknameByEmail(email);
            String name = userService.getNameByEmail(email);
            String profileImg = userService.getProfileImageURIByEmail(email);
            session.setAttribute("userInfo", UserInfoSession.builder()
                    .nickname(nickname)
                    .email(email)
                    .name(name)
                    .profileImage(profileImg)
                    .build()
            );
        }
        response.sendRedirect("/");
    }
}
