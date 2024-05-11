package com.y.java_board.config.security;

import com.y.java_board.config.UserInfoSession;
import com.y.java_board.domain.User;
import com.y.java_board.repository.UserRepository;
import com.y.java_board.service.BlobStorageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final BlobStorageService blobStorageService;
    private final ResourceLoader resourceLoader;

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
            byte[] profileImg;
            if (user.getProfileImageURL().equals("default"))
                profileImg = getDefaultProfileImageBytes();
            else
                profileImg = blobStorageService.readFile(user.getProfileImageURL());

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

    private Resource getDefaultProfileImage() {
        String defaultProfileImageLocation = "classpath:/static/images/profile/default_profile.png";
        return resourceLoader.getResource(defaultProfileImageLocation);
    }

    private byte[] getDefaultProfileImageBytes() throws IOException {
        Resource defaultProfileImageResource = getDefaultProfileImage();
        Path path = Paths.get(defaultProfileImageResource.getURI());
        return Files.readAllBytes(path);
    }
}
