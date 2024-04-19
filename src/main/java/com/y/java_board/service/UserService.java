package com.y.java_board.service;


import com.y.java_board.domain.User;
import com.y.java_board.repository.ArticleRepository;
import com.y.java_board.repository.CommentRepository;
import com.y.java_board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Optional;

@Service
@AllArgsConstructor
@SessionAttributes("loggedInUser")
public class UserService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public User register(User user) throws IllegalStateException {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("[중복 이메일] 이미 가입된 이메일 입니다.");
                });
        userRepository.findByNickname(user.getNickname())
                .ifPresent(u -> {
                    throw new IllegalStateException("[중복 닉네임] 이미 존재하는 닉네임 입니다.");
                });
        return userRepository.save(user);
    }

    public String getNicknameByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(User::getNickname)
                .orElseThrow(() -> new IllegalArgumentException("[존재하지 않는 이메일] 해당 이메일의 사용자를 찾을 수 없습니다."));
    }

    public User updateUserProfile(User user){
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            String existingNickname = userOptional.get().getNickname();

            userOptional.get().setNickname(user.getNickname());
            if(user.getProfileImage() != null) {
                userOptional.get().setProfileImage(user.getProfileImage());
            }

            if(!existingNickname.equals(user.getNickname())) {
                articleRepository.findByWriter(existingNickname)
                                .forEach(article -> article.setWriter(user.getNickname()));
                commentRepository.findByWriter(existingNickname)
                                .forEach(comment -> comment.setWriter(user.getNickname()));
            }
            return userRepository.save(userOptional.get());
        }
        throw new IllegalStateException("[존재하지 않는 이메일] 해당 이메일 사용자가 없어서 프로필 정보를 업데이트 할 수 없습니다.");
    }

    public String getProfileImageURIByEmail(String email) {
        String profileImageString = "default_profile.png";
        return userRepository.findByEmail(email).map(User::getProfileImage)
                .filter(profileImage -> !profileImage.isEmpty())
                .orElse(profileImageString);
    }

    public String getNameByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getName).orElseThrow(() -> new IllegalArgumentException("[존재하지 않는 이메일] 해당 이메일의 사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public void deleteUser(String email){
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


}
