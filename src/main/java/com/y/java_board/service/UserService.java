package com.y.java_board.service;


import com.y.java_board.domain.User;
import com.y.java_board.dto.UserDto;
import com.y.java_board.repository.ArticleRepository;
import com.y.java_board.repository.CommentRepository;
import com.y.java_board.repository.UserRepository;
import com.y.java_board.util.ImageUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@AllArgsConstructor
@SessionAttributes("loggedInUser")
public class UserService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResourceLoader resourceLoader;

    public User registerNewUserAccount(UserDto userDto) throws IllegalStateException, IOException {
        if (emailExists(userDto.email())) {
            throw new IllegalStateException("중복 이메일");
        }
        if (nicknameExists(userDto.nickname())) {
            throw new IllegalStateException("중복 닉네임");
        }
        User user = userDto.toEntity();
        user.setPassword(passwordEncoder.encode(userDto.password()));

        user.setProfileImage(ImageUtil.compressImage(getDefaultProfileImageBytes()));

        return userRepository.save(user);
    }

    public User updateUserProfile(String email, String nickname, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String existingNickname = userOptional.get().getNickname();
            userOptional.get().setNickname(nickname);
            if (file != null) {
                byte[] newImage = ImageUtil.compressImage(file.getBytes());
                userOptional.get().setProfileImage(newImage);
            }

            if (!existingNickname.equals(nickname)) {
                articleRepository.findByWriter(existingNickname)
                        .forEach(article -> article.setWriter(nickname));
                commentRepository.findByWriter(existingNickname)
                        .forEach(comment -> comment.setWriter(nickname));
            }
            return userRepository.save(userOptional.get());
        }
        throw new IllegalStateException("[존재하지 않는 이메일] 해당 이메일 사용자가 없어서 프로필 정보를 업데이트 할 수 없습니다.");
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("[존재하지 않는 이메일] 해당 이메일의 사용자가 없어서 삭제할 수 없습니다."));
        commentRepository.findByWriter(user.getNickname())
                .forEach(comment -> commentRepository.deleteById(comment.getId()));
        articleRepository.findByWriter(user.getNickname())
                .forEach(article -> {
                    commentRepository.deleteByArticleId(article.getId());
                    articleRepository.deleteById(article.getId());
                });
        userRepository.deleteById(user.getId());
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("[존재하지 않는 이메일] "));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean nicknameExists(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
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
