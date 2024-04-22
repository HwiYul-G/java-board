package com.y.java_board.config;

import com.y.java_board.domain.User;
import com.y.java_board.repository.UserRepository;
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

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).get();
            String nickname = user.getNickname();
            String name = user.getName();
            String profileImg = user.getProfileImage();
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
