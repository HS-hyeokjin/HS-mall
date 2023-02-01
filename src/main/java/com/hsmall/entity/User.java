package com.hsmall.entity;

import com.hsmall.constant.Role;
import com.hsmall.dto.UserRegisterFormDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity
@Table
@Getter
@Setter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;


    public static User createUser(UserRegisterFormDto userRegisterFormDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setName(userRegisterFormDto.getName());
        user.setEmail(userRegisterFormDto.getEmail());
        user.setAddress(userRegisterFormDto.getAddress());
        String password = passwordEncoder.encode(userRegisterFormDto.getPassword());
        user.setPassword(password);
        user.setRole(Role.ADMIN);
        return user;
    }
}
