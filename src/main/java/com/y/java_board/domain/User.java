package com.y.java_board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;
    private String password;
    private String name;
    private String nickname;
    @Column(name = "profile_image_url")
    private String profileImageURL;

    public User(String email, String password, String name, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

}
