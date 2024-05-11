package com.y.java_board.service;


import com.y.java_board.domain.Storage;
import com.y.java_board.domain.User;
import com.y.java_board.dto.UserDto;
import com.y.java_board.repository.ArticleRepository;
import com.y.java_board.repository.CommentRepository;
import com.y.java_board.repository.UserRepository;
import com.y.java_board.util.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_PROFILE_CONTAINER = "profile";

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResourceLoader resourceLoader;
    private final BlobStorageService blobStorageService;

    public User registerNewUserAccount(UserDto userDto) throws IllegalStateException, IOException {
        if (emailExists(userDto.email())) {
            throw new IllegalArgumentException("중복 이메일");
        }
        if (nicknameExists(userDto.nickname())) {
            throw new IllegalArgumentException("중복 닉네임");
        }
        UserValidator.validateEmail(userDto.email());
        UserValidator.validatePassword(userDto.password());

        User user = userDto.toEntity();
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setProfileImageURL("default");
        return userRepository.save(user);
    }

    public User updateUserProfile(String email, String nickname, MultipartFile file) throws IOException, IllegalAccessException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String existingNickname = userOptional.get().getNickname();
            userOptional.get().setNickname(nickname);
            if (file != null) { // 파일이 존재하는 경우
                Storage storage = new Storage(USER_PROFILE_CONTAINER, file.getOriginalFilename(), file.getInputStream());
                blobStorageService.delete(userOptional.get().getProfileImageURL());
                String uri = blobStorageService.writeFile(storage);
                userOptional.get().setProfileImageURL(uri);
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
        if(!user.getProfileImageURL().equals("default"))
            blobStorageService.delete(user.getProfileImageURL());
        commentRepository.findByWriter(user.getNickname())
                .forEach(comment -> commentRepository.deleteById(comment.getId()));
        articleRepository.findByWriter(user.getNickname())
                .forEach(article -> {
                    commentRepository.deleteByArticleId(article.getId());
                    articleRepository.deleteById(article.getId());
                });
        userRepository.deleteById(user.getId());
    }

    public boolean changeUserPassword(User user, String password) {
        UserValidator.validatePassword(password);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
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

}
